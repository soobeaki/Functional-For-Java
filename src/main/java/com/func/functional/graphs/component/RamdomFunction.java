package com.func.functional.graphs.component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.func.functional.configs.properties.ApiKeyConfigProperties;
import com.func.functional.configs.properties.ApiServerConfigProperties;
import com.func.functional.graphs.model.ChartModel;
import com.func.functional.utils.DateUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RamdomFunction {

	private final WebClient.Builder webClientBuilder;

	private final ApiServerConfigProperties apiServerConfigProperties;

	private final ApiKeyConfigProperties apiKeyConfigProperties;

	public void randomChart() {

		WebClient webclient = webClientBuilder.build();

		String holidayInfo = webclient.get()
				.uri(apiServerConfigProperties.getApiProperties("OPEN").getDomain() + "getRestDeInfo")
				.headers(header -> {
					header.set("solYear", String.valueOf(LocalDateTime.now().getYear()));
					header.set("_type", "json");
					header.set("numOfRows", "100");
					header.set("ServiceKey", apiKeyConfigProperties.getApiKey("holidayInfo"));
				}).retrieve().bodyToMono(String.class).block();

		log.info("holidayInfo : {}", holidayInfo);
		/**
		 * 랜덤 수익률 차트
		 */
		BigDecimal diffMonths = new BigDecimal(DateUtils.getMonthsDifference("20240101", "20250101"));

		if (diffMonths.equals(new BigDecimal(0))) {

			List<ChartModel> getChartMonth = new ArrayList<ChartModel>();
			String prev = "";
			int geti = 0;

			for (int i = 0; i < getChartMonth.size(); i++) {
				System.out.println("getChartMonth.get(i).getXAxis().substring(4, 6)"
						+ getChartMonth.get(i).getXAxis().substring(4, 6));
				if (!prev.equals(getChartMonth.get(i).getXAxis().substring(4, 6))) {
					geti++;
				}
				prev = getChartMonth.get(i).getXAxis().substring(4, 6);

			}

			diffMonths = new BigDecimal(geti);
			System.out.println("geti ==========>" + geti);
		}
		System.out.println("diffMonths  ============> " + diffMonths);
		BigDecimal targetProfitRate = new BigDecimal(Math.random() - 0.5).multiply(new BigDecimal(11))
				.subtract(new BigDecimal("-3"));
		BigDecimal prevMonthlyProfitRate = BigDecimal.ZERO;
	}

}
