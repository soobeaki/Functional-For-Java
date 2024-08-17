package com.func.functional.biz.graphs.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.func.functional.biz.client.ApiClient;
import com.func.functional.biz.graphs.model.ChartModel;
import com.func.functional.configs.properties.ApiServerConfigProperties;
import com.func.functional.utils.DateUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 서비스 계층의 클래스입니다.
 * 
 * <p>이 클래스는 랜덤 그래프 생성 로직을 처리합니다.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RandomGraphService {

    /** ApiClient */
    private final ApiClient apiClient;

    /** ApiServer */
    private final ApiServerConfigProperties apiServerConfigProperties;

    /** ObjectMapper */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /** DateTimeFormatter (yyyyMMdd 형식) */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    // 상수 정의 (매직 넘버 제거)
    private static final BigDecimal TARGET_PROFIT_MULTIPLIER = new BigDecimal(11);
    private static final BigDecimal RANDOM_VARIATION_RANGE = new BigDecimal(3);
    private static final BigDecimal RANDOM_OFFSET = new BigDecimal(-0.5);

    /**
     * 주어진 기간의 랜덤 수익률 차트를 생성
     * 
     * @param fromDate 시작 날짜 (yyyyMMdd 형식)
     * @param toDate      종료 날짜 (yyyyMMdd 형식)
     */
    public void randomChart(String fromDate, String toDate) {
	// 공휴일 정보를 API로부터 가져옴
	Set<String> holidayDates = getHolidaysFromApi();

	// 시작일과 종료일 사이의 영업일 리스트를 가져옴
	List<ChartModel> businessDays = getBusinessDay(fromDate, toDate, holidayDates);

	// 랜덤 수익률 데이터 생성
	List<ChartModel> profitRateData = generateRandomProfitRates(fromDate, toDate, businessDays);

	// 생성된 수익률 데이터를 로그로 출력
	log.info("Profit Rate Data: {}", profitRateData);
    }

    /**
     * API를 호출하여 해당 연도의 공휴일 데이터를 가져옴
     * 
     * @return 공휴일 날짜들이 저장된 Set
     */
    private Set<String> getHolidaysFromApi() {
	// 공휴일 정보를 가져오기 위한 API 호출
	String holidayApiResponse = apiClient.get(
		apiServerConfigProperties.getApiProperties("holidayInfo").getDomain() + "getRestDeInfo",
		Map.of(
			"solYear", String.valueOf(LocalDateTime.now().getYear()), 
			"_type", "json",
			"numOfRows", "100",
			"ServiceKey", apiServerConfigProperties.getApiProperties("holidayInfo").getKey()
		),
		String.class);

	try {
	    // JSON 응답을 파싱하여 공휴일 날짜를 Set에 저장
	    JsonNode itemsNode = objectMapper.readTree(holidayApiResponse)
		    .path("response")
		    .path("body")
		    .path("items")
		    .path("item");
	    
	    Set<String> holidaySet = new HashSet<>();
	    itemsNode.forEach(item -> holidaySet.add(item.path("locdate").asText()));
	    return holidaySet;
	} catch (Exception e) {
	    // JSON 파싱 중 에러가 발생한 경우 로그 출력
	    if (log.isErrorEnabled()) {
		log.error("Failed JSON parsing: {}", e.getMessage(), e);
	    }
	    return new HashSet<>();
	}
    }

    /**
     * 시작일과 종료일 사이의 영업일을 계산 (주말과 공휴일 제외)
     * 
     * @param fromDate   시작 날짜 (yyyyMMdd 형식)
     * @param toDate        종료 날짜 (yyyyMMdd 형식)
     * @param holidaySet 공휴일 목록
     * @return 영업일이 저장된 ChartModel 리스트
     */
    private List<ChartModel> getBusinessDay(String fromDate, String toDate, Set<String> holidaySet) {
	List<ChartModel> businessDays = new ArrayList<>();
	LocalDate start = LocalDate.parse(fromDate, DATE_FORMATTER);
	LocalDate end = LocalDate.parse(toDate, DATE_FORMATTER);

	// 시작일에서 종료일까지의 날짜 중 토요일과 일요일을 제외하고 출력
	while (!start.isAfter(end)) {
	    // 토요일과 일요일 제외, 공휴일 제외
	    if (start.getDayOfWeek() != DayOfWeek.SATURDAY 
		    && start.getDayOfWeek() != DayOfWeek.SUNDAY
		    && !holidaySet.contains(start.format(DATE_FORMATTER))) {
		
		ChartModel chartModel = new ChartModel();
		chartModel.setXAxis(start.format(DATE_FORMATTER));
		businessDays.add(chartModel); // 영업일 추가
	    }

	    // 다음 날로 이동
	    start = start.plusDays(1);
	}

	return businessDays;
    }

    /**
     * 랜덤 수익률 데이터를 생성
     * 
     * @param fromDate         시작 날짜 (yyyyMMdd 형식)
     * @param toDate              종료 날짜 (yyyyMMdd 형식)
     * @param businessDays 영업일 리스트
     * @return 생성된 수익률 데이터가 저장된 ChartModel 리스트
     */
    private List<ChartModel> generateRandomProfitRates(String fromDate, String toDate, List<ChartModel> businessDays) {
	// 시작일과 종료일 사이의 월 차이를 계산
	BigDecimal monthsBetween = new BigDecimal(DateUtils.getMonthsDifference(fromDate, toDate));
	
	// 타겟 수익률을 랜덤하게 설정
	BigDecimal targetProfitRate = new BigDecimal(Math.random() - RANDOM_OFFSET.doubleValue())
		.multiply(TARGET_PROFIT_MULTIPLIER)
		.subtract(RANDOM_VARIATION_RANGE);
	
	// 이전 월의 수익률 초기화
	BigDecimal previousMonthlyProfitRate = BigDecimal.ZERO;

	List<ChartModel> profitRateData = new ArrayList<>();

	// 각 월별 수익률을 계산
	for (int i = 0; i < monthsBetween.intValue(); i++) {
	    // 타겟 수익률을 월별로 분할하여 기준 수익률 설정
	    BigDecimal monthlyProfitRateBase = targetProfitRate.divide(monthsBetween, new MathContext(2))
		    .multiply(new BigDecimal(i + 1));
	    
	    // 기준값에서 -1.5 ~ +1.5 범위로 랜덤하게 설정한 월별 수익률
	    BigDecimal monthlyProfitRate = monthlyProfitRateBase
		    .add(new BigDecimal(Math.random() - RANDOM_OFFSET.doubleValue())
			    .multiply(RANDOM_VARIATION_RANGE, new MathContext(2)));

	    // 현재 월의 날짜(년월) 추출
	    String currentYearMonth = DateUtils.plus(fromDate, i, "MONTH").substring(0, 6);
	    
	    // 현재 월의 영업일을 추출
	    List<String> currentMonthBusinessDays = new ArrayList<>();
	    for (int k = 0; k < businessDays.size(); k++) {
		if (businessDays.get(k).getXAxis().substring(0, 6).equals(currentYearMonth)) {
		    currentMonthBusinessDays.add(businessDays.get(k).getXAxis());
		}
	    }

	    // 각 영업일에 대한 일별 수익률을 계산
	    for (int j = 0, daysInMonth = currentMonthBusinessDays.size(); j < daysInMonth; j++) {
		// 이전월 수익률 + ((당월 수익률 - 이전월(첫번째달에는 0)) / 일수(daysInMonth) * (j+1))
		BigDecimal dailyProfitRateBase = previousMonthlyProfitRate
			.add(monthlyProfitRate.subtract(previousMonthlyProfitRate)
				.divide(new BigDecimal(daysInMonth * (j + 1)), new MathContext(2)));
		
		// 일별 수익률에 -3 ~ +3 범위의 랜덤값을 추가
		BigDecimal dailyProfitRate = dailyProfitRateBase
			.add(new BigDecimal(Math.random() - RANDOM_OFFSET.doubleValue())
				.multiply(RANDOM_VARIATION_RANGE, new MathContext(2)));

		// 일별 수익률을 ChartModel에 저장
		ChartModel chartModel = new ChartModel();
		chartModel.setXAxis(currentMonthBusinessDays.get(j));
		chartModel.setYAxis(dailyProfitRate.setScale(2, RoundingMode.DOWN));
		profitRateData.add(chartModel);

	    }

	    // 이전 월 수익률을 업데이트
	    previousMonthlyProfitRate = monthlyProfitRate;
	}

	// 첫 번째 수익률을 0으로 설정 (초기값)
	if (!profitRateData.isEmpty()) {
	    profitRateData.get(0).setYAxis(BigDecimal.ZERO);
	}

	return profitRateData;
    }
}
