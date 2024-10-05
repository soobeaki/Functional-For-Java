package com.func.functional.filter.wrapper;

import java.nio.charset.StandardCharsets;

import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.http.HttpServletResponse;

/**
 * ResponseWrapper 클래스
 * 
 * {@link ContentCachingResponseWrapper}를 확장하여 응답 본문을 캐시하고 응답 본문을 문자열로 쉽게 가져올 수
 * 있는 기능을 추가합니다.
 */
public class ResponseWrapper extends ContentCachingResponseWrapper {

    /**
     * 생성자
     * 
     * @param response 원래의 HttpServletResponse 객체
     */
    public ResponseWrapper(HttpServletResponse response) {
	super(response);
    }

    /**
     * 응답 본문을 문자열로 가져옵니다.
     * 
     * @return 응답 본문을 UTF-8 인코딩으로 변환한 문자열
     */
    public String getResponseBody() {
	return new String(getContentAsByteArray(), StandardCharsets.UTF_8);
    }
}
