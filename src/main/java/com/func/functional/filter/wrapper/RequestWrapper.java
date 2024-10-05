package com.func.functional.filter.wrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
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
	System.out.println("Request Body Bytes: " + Arrays.toString(body));
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
     * InputStream 재정의: 요청 본문을 필터 체인에서 다시 읽을 수 있도록 처리.
     * 
     * @return 새롭게 만든 ServletInputStream
     */
    @Override
    public ServletInputStream getInputStream() {
	final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

	return new ServletInputStream() {
	    @Override
	    public int read() throws IOException {
		// ByteArrayInputStream에서 바이트를 읽음
		return byteArrayInputStream.read();
	    }

	    @Override
	    public boolean isFinished() {
		// 모든 바이트가 읽혔는지 확인
		return byteArrayInputStream.available() == 0;
	    }

	    @Override
	    public boolean isReady() {
		// 읽을 준비가 되었는지 확인
		return true;
	    }

	    @Override
	    public void setReadListener(ReadListener readListener) {
		// 리스너를 설정하는 메서드 (필요 시 구현)
	    }

	};
    }

    /**
     * 요청의 쿼리 문자열을 가져옵니다.
     *
     * @return 요청의 쿼리 문자열
     */
    @Override
    public String getQueryString() {
	// 부모 클래스의 getQueryString 메서드를 호출하여 쿼리 문자열 반환
	return super.getQueryString();
    }
}
