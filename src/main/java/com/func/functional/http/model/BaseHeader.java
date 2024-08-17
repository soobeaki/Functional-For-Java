package com.func.functional.http.model;

/**
 * HTTP 헤더에 사용되는 상수들을 정의하는 클래스입니다.
 * 
 * 이 클래스는 HTTP 요청과 응답에서 사용되는 표준 헤더 필드의 이름을 상수로 정의합니다.
 * 상수는 불변이며, 요청과 응답을 처리하는 데 유용합니다.
 * 
 * 주의: 이 클래스는 인스턴스를 생성할 수 없도록 설계되었습니다.
 */
public class BaseHeader {

    /**
     * 이 클래스의 인스턴스화를 방지.
     * 
     * @throws IllegalStateException 항상 발생하여 이 클래스의 인스턴스가 생성될 수 없음을 보장합니다.
     */
    private BaseHeader() throws IllegalStateException {
        throw new IllegalStateException(this.getClass().getPackageName() + "." + this.getClass().getSimpleName());
    }

    /** 'X-Request-Id' */
    public static final String REQUEST_ID = "X-Request-Id";

    /** 'X-Crypto-Apply' */
    public static final String CRYPTO_APPLY = "X-Crypto-Apply";

    /** 'X-User-Id' */
    public static final String USER_ID = "X-User-Id";

    /** 'X-Role' */
    public static final String ROLE = "X-Role";

    /** 'X-Timestamp' */
    public static final String TIMESTAMP = "X-Timestamp";
}
