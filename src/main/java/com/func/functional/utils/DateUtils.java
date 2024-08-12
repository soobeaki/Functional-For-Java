package com.func.functional.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;

/**
 * 날짜 및 시간과 관련된 유틸리티 메소드를 제공하는 클래스입니다.
 */
public class DateUtils {

	/**
	 * 객체를 생성할 수 없게 하기 위한 private 생성자
	 */
	private DateUtils() {
		throw new IllegalStateException(this.getClass().getPackageName() + "." + this.getClass().getSimpleName());
	}

	private static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

	/**
	 * 두 날짜 사이의 월 수를 계산합니다.
	 * 
	 * @param startDate 시작 날짜 (yyyyMMdd 형식)
	 * @param endDate   종료 날짜 (yyyyMMdd 형식)
	 * @return 두 날짜 사이의 월 수
	 * 
	 * @throws IllegalArgumentException 날짜 형식이 잘못된 경우 발생
	 */
	public static Integer getMonthsDifference(String startDate, String endDate) {
		LocalDate fromLd = LocalDate.of(Integer.parseInt(startDate.substring(0, 4)),
				Integer.parseInt(startDate.substring(4, 6)), Integer.parseInt(startDate.substring(6, 8)));
		LocalDate toLd = LocalDate.of(Integer.parseInt(endDate.substring(0, 4)),
				Integer.parseInt(endDate.substring(4, 6)), Integer.parseInt(endDate.substring(6, 8)));
		Period period = Period.between(fromLd, toLd);
		return period.getYears() * 12 + period.getMonths();
	}

	/**
	 * 주어진 날짜 문자열의 연도 분기를 반환합니다.
	 * 
	 * @param dateStr 날짜 문자열 (yyyyMMdd 형식)
	 * @return 연도 분기
	 */
	public static Integer getQuarter(String dateStr) {
		LocalDate ld = LocalDate.of(Integer.parseInt(dateStr.substring(0, 4)),
				Integer.parseInt(dateStr.substring(4, 6)), Integer.parseInt(dateStr.substring(6, 8)));
		return ld.get(IsoFields.QUARTER_OF_YEAR);
	}

	/**
	 * 주어진 월의 연도 분기를 반환합니다.
	 * 
	 * @param month 월 (1: 1월, 2: 2월 등)
	 * @return 연도 분기
	 */
	public static Integer getQuarter(Integer month) {
		LocalDate ld = LocalDate.of(2000, month, 1);
		return ld.get(IsoFields.QUARTER_OF_YEAR);
	}

	/**
	 * 현재 날짜와 시간을 주어진 형식에 맞게 반환합니다.
	 * 
	 * @param format 원하는 날짜 형식 패턴
	 * @return 현재 날짜와 시간을 형식화된 문자열로 반환
	 */
	public static String getCurrentDate(String format) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 현재 날짜를 yyyyMMdd 형식으로 반환합니다.
	 * 
	 * @return 현재 날짜를 yyyyMMdd 형식으로 반환
	 */
	public static String getCurrentDate() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
	}

	/**
	 * 현재 날짜와 시간을 yyyyMMddHHmmss 형식으로 반환합니다.
	 * 
	 * @return 현재 날짜와 시간을 yyyyMMddHHmmss 형식으로 반환
	 */
	public static String getCurrentDatetime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}

	/**
	 * 현재 날짜와 시간을 LocalDateTime 객체로 반환합니다.
	 * 
	 * @return 현재 날짜와 시간을 LocalDateTime 객체로 반환
	 */
	public static LocalDateTime now() {
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * 주어진 형식에 맞게 날짜 문자열을 LocalDateTime 객체로 파싱합니다.
	 * 
	 * @param dateStr 파싱할 날짜 문자열
	 * @param format  날짜 문자열의 형식 패턴
	 * @return 파싱된 LocalDateTime 객체
	 */
	public static LocalDateTime toLocalDateTime(String dateStr, String format) {
		return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 주어진 형식에 맞게 날짜 문자열을 LocalDate 객체로 파싱합니다.
	 * 
	 * @param dateStr 파싱할 날짜 문자열
	 * @param format  날짜 문자열의 형식 패턴
	 * @return 파싱된 LocalDate 객체
	 */
	public static LocalDate toLocalDate(String dateStr, String format) {
		return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(format));
	}

	/**
	 * yyyyMMdd 형식의 날짜 문자열을 LocalDate 객체로 파싱합니다.
	 * 
	 * @param dateStr yyyyMMdd 형식의 날짜 문자열
	 * @return 파싱된 LocalDate 객체
	 */
	public static LocalDate toLocalDate(String dateStr) {
		return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
	}

}
