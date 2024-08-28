package com.func.functional.http.client;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import com.func.functional.utils.ServletUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring WebFlux의 WebClient를 사용하여 비동기 및 논블로킹 HTTP 요청을 수행하는 클래스입니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HttpClient {

    /** WebClient 인스턴스 */
    private final WebClient webClient;

    /**
     * 주어진 URI로 HTTP 요청을 수행하고 응답을 지정된 타입으로 반환합니다.
     *
     * @param <T>          응답 타입
     * @param method       HTTP 메서드 (GET, POST, PUT, DELETE 등)
     * @param fullUrl      요청할 URI
     * @param requestBody  요청 본문 (POST, PUT 요청 시 사용)
     * @param responseType 응답을 매핑할 타입
     * @return 요청에 대한 응답을 매핑한 객체
     */
    public <T> T request(HttpMethod httpMethod, URI requestUri, Object requestPayload, Class<T> responseType) {
        interfaceLog(httpMethod, requestUri, requestPayload);

        RequestHeadersSpec<?> requestSpec = createRequestSpec(httpMethod, requestUri, requestPayload);
        return requestSpec // 요청할 URI 설정
                .retrieve() // 요청 수행 및 응답 수신
                .bodyToMono(responseType) // 응답 본문을 지정된 타입으로 변환
                .block(); // 응답을 블로킹 방식으로 기다림
    }

    /**
     * HTTP 메서드와 URI에 따라 적절한 요청 사양(Request Specification)을 생성합니다.
     *
     * @param method      HTTP 메서드
     * @param fullUrl     요청할 URI
     * @param requestBody 요청 본문 (POST, PUT, DELETE 요청 시 사용)
     * @return 생성된 RequestHeadersSpec 객체
     */
    private RequestHeadersSpec<?> createRequestSpec(HttpMethod httpMethod, URI requestUri, Object requestPayload) {
        RequestBodyUriSpec uriSpec = webClient.method(httpMethod);

        if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT || httpMethod == HttpMethod.DELETE) {
            return uriSpec.uri(requestUri).bodyValue(requestPayload);
        }

        return uriSpec.uri(requestUri);
    }

    /**
     * HTTP 요청의 정보를 로그로 기록합니다.
     *
     * @param httpMethod     HTTP 메서드
     * @param requestUri     요청할 URI
     * @param requestPayload 요청 본문 (POST, PUT 요청 시 사용)
     */
    private void interfaceLog(HttpMethod httpMethod, URI requestUri, Object requestPayload) {
        if (log.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder().append(System.lineSeparator());
            sb.append("[HTTP REQUEST DATA]").append(System.lineSeparator());
            sb.append("Headers:     " + ServletUtils.getRequestHeader()).append(System.lineSeparator());
            sb.append("Method:      " + httpMethod.toString()).append(System.lineSeparator());
            sb.append("Url:         " + requestUri.toString()).append(System.lineSeparator());
            sb.append("RequestBody: " + Optional.ofNullable(requestPayload).map(Object::toString).orElse("null"));
            log.info(sb.toString());
        }
    }
}
