package com.func.functional.filter.wrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * RequestWrapper 클래스
 * 
 * {@link HttpServletRequestWrapper}를 확장하여 요청 본문을 캐시하고 요청 본문을 문자열로 쉽게 가져올 수 있는
 * 기능을 추가합니다.
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    /** 요청 본문을 저장하는 바이트 배열 */
    private final byte[] body;

    /**
     * 생성자
     * 
     * @param request 원래의 HttpServletRequest 객체
     * @throws IOException 요청 본문을 읽는 동안 발생할 수 있는 예외
     */
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 요청 본문을 바이트 배열로 읽어 저장
        body = request.getInputStream().readAllBytes();
    }

    /**
     * 요청 본문을 문자열로 가져옵니다.
     * 
     * @return 요청 본문을 UTF-8 인코딩으로 변환한 문자열
     */
    public String getBody() {
        return new String(body, StandardCharsets.UTF_8);
    }

    /**
     * 쿼리 문자열을 가져옵니다.
     * 
     * @return 요청의 쿼리 문자열
     */
    @Override
    public String getQueryString() {
        return super.getQueryString();
    }
}
