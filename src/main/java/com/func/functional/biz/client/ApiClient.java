package com.func.functional.biz.client;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.func.functional.configs.properties.ApiServerConfigProperties;
import com.func.functional.http.client.HttpClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ApiClient는 HTTP 요청을 수행하기 위한 클라이언트입니다.
 * 
 * <p>
 * 이 클래스는 HTTP GET, POST, PUT, DELETE 요청을 간편하게 수행할 수 있는 메소드를 제공합니다. 요청 시 URI와 쿼리
 * 파라미터를 자동으로 구성하여 최종 URL을 생성합니다.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApiClient {

    /** HTTP 요청을 실제로 수행하는 클라이언트 */
    private final HttpClient client;

    /** API 서버의 기본 속성 정보를 관리하는 클래스 */
    private final ApiServerConfigProperties apiServerConfigProperties;

    /**
     * 주어진 URI와 쿼리 파라미터를 사용하여 GET 요청을 수행합니다.
     * 
     * @param endpoint     요청할 API의 엔드포인트
     * @param queryParams  쿼리 파라미터를 담고 있는 객체 (Map 또는 Bean)
     * @param responseType 응답의 타입
     * @param <T>          응답 타입
     * @return 요청에 대한 응답
     */
    public <T> T get(String apiEndpoint, Object queryParameters, Class<T> responseType) {
        return client.request(HttpMethod.GET, generateRequestUri(apiEndpoint, queryParameters), null, responseType);
    }

    /**
     * 주어진 URI와 요청 본문을 사용하여 POST 요청을 수행합니다.
     * 
     * @param endpoint     요청할 API의 엔드포인트
     * @param requestBody  요청 본문 객체
     * @param responseType 응답의 타입
     * @param <T>          응답 타입
     * @return 요청에 대한 응답
     */
    public <T> T post(String apiEndpoint, Object requestPayload, Class<T> responseType) {
        return client.request(HttpMethod.POST, generateRequestUri(apiEndpoint, null), requestPayload, responseType);
    }

    /**
     * 주어진 URI와 요청 본문을 사용하여 PUT 요청을 수행합니다.
     * 
     * @param endpoint     요청할 API의 엔드포인트
     * @param requestBody  요청 본문 객체
     * @param responseType 응답의 타입
     * @param <T>          응답 타입
     * @return 요청에 대한 응답
     */
    public <T> T put(String apiEndpoint, Object requestPayload, Class<T> responseType) {
        return client.request(HttpMethod.PUT, generateRequestUri(apiEndpoint, null), requestPayload, responseType);
    }

    /**
     * 주어진 URI와 요청 본문을 사용하여 DELETE 요청을 수행합니다.
     * 
     * @param endpoint     요청할 API의 엔드포인트
     * @param requestBody  요청 본문 객체
     * @param responseType 응답의 타입
     * @param <T>          응답 타입
     * @return 요청에 대한 응답
     */
    public <T> T delete(String apiEndpoint, Object requestPayload, Class<T> responseType) {
        return client.request(HttpMethod.DELETE, generateRequestUri(apiEndpoint, null), requestPayload, responseType);
    }

    /**
     * URI와 쿼리 파라미터를 결합하여 전체 URL을 생성합니다.
     * 
     * @param endpoint    요청할 API의 엔드포인트
     * @param queryParams 쿼리 파라미터를 담고 있는 객체 (Map 또는 Bean)
     * @return 생성된 URI
     */
    private URI generateRequestUri(String apiEndpoint, Object queryParameters) {
        // 기본 URI를 생성합니다.
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiServerConfigProperties.getApiProperties(apiEndpoint));

        // 쿼리 파라미터를 URL에 추가합니다.
        if (queryParameters instanceof Map) {
            // 쿼리 파라미터가 Map인 경우
            Map<?, ?> paramMap = (Map<?, ?>) queryParameters;
            paramMap.forEach((key, value) -> {
                if (key instanceof String && value != null) {
                    // 파라미터 키와 값을 URL에 추가합니다.
                    uriBuilder.queryParam(key.toString(), value.toString());
                }
            });
        } else if (queryParameters != null) {
            // 쿼리 파라미터가 Bean인 경우
            try {
                // Bean의 프로퍼티를 추출합니다.
                PropertyDescriptor[] propertyDescriptors = java.beans.Introspector.getBeanInfo(queryParameters.getClass(), Object.class)
                        .getPropertyDescriptors();

                Stream.of(propertyDescriptors).map(PropertyDescriptor::getReadMethod).filter(Objects::nonNull).forEach(getter -> {
                    try {
                        // 프로퍼티 값을 읽고 URL에 추가합니다.
                        Object value = getter.invoke(queryParameters);
                        if (value != null) {
                            uriBuilder.queryParam(getter.getName(), value.toString());
                        }
                    } catch (Exception e) {
                        // 프로퍼티 값 접근 중 오류 발생 시 로그를 남깁니다.
                        if (log.isErrorEnabled()) {
                            log.error("Error accessing property with getter : {}", getter.getName(), e);
                        }
                    }
                });
            } catch (IntrospectionException e) {
                // Bean 정보 추출 중 오류 발생 시 로그를 남깁니다.
                if (log.isErrorEnabled()) {
                    log.error("Failed to introspect the bean : {}", queryParameters.getClass().getName(), e);
                }
            }
        }

        // 쿼리 파라미터가 추가된 전체 URI를 반환합니다.
        return uriBuilder.build(true).toUri();
    }
}
