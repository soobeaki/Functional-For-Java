package com.func.functional.utils;

/**
 * String 타입과 관련된 유틸리티 메소드를 제공하는 클래스입니다.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 객체를 생성할 수 없게 하기 위한 private 생성자
     */
    private StringUtils() {
	throw new IllegalStateException(this.getClass().getPackageName() + "." + this.getClass().getSimpleName());
    }

    /**
     * 주어진 문자열을 마스킹 처리하여 첫 글자와 마지막 글자만 남기고 나머지를 '*'로 대체합니다.
     * 
     * @param input 마스킹할 문자열
     * @return 첫 글자와 마지막 글자를 제외한 마스킹된 문자열
     */
    public static String maskString(String input) {
	if (input == null) {
	    throw new IllegalArgumentException("Input cannot be null");
	}

	if (input.length() < 2) {
	    // 한 글자만 있는 경우 마스킹하지 않음
	    return input;
	}

	if (input.length() == 2) {
	    return input.charAt(0) + "*";
	}

	String firstChar = input.substring(0, 1);
	String lastChar = input.substring(input.length() - 1);
//	String mask = Stream.generate(() -> "*").limit(input.length() - 2).collect(Collectors.joining());
	String mask = "*".repeat(input.length() - 2);

	return firstChar + mask + lastChar;
    }

}
