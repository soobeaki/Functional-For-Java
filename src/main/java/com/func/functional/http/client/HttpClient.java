package com.func.functional.http.client;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 이 클래스는 Spring WebFlux의 WebClient 비동기 및 논블로킹 HTTP 요청을 지원
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HttpClient {

    /** WebClient 인스턴스 */
    private final WebClient webClient;

    /**
     * 주어진 URI로 HTTP GET 요청을 수행하고 응답을 지정된 타입으로 반환합니다.
     * 
     * @param <T>          응답 타입
     * @param fullUrl      요청할 URI
     * @param responseType 응답을 매핑할 타입
     * @return 요청에 대한 응답을 매핑한 객체
     */
    public <T> T get(URI fullUrl, Class<T> responseType) {
	return webClient.get()				// GET 요청 설정
        		.uri(fullUrl)					// 요청할 URI 설정
        		.retrieve()					// 요청 수행 및 응답 수신
        		.bodyToMono(responseType)  // 응답 본문을 지정된 타입으로 변환
        		.block();						// 응답을 블로킹 방식으로 기다림
    }

}
