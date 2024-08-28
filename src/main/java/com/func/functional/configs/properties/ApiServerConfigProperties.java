package com.func.functional.configs.properties;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * API 서버에 대한 구성 속성을 로드하는 클래스입니다.
 * 
 * <p>
 * 이 클래스는 애플리케이션의 설정 파일에서 'api-server' 접두어를 가진 속성을 로드하고, API 서버 정보를 처리하는 메소드를
 * 제공합니다.
 * </p>
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api-server")
public class ApiServerConfigProperties {

    /** API 서버 목록 */
    private List<ApiServer> servers;

    /**
     * API 서버 정보 클래스
     * 
     * <p>
     * 각 API 서버의 이름, 도메인, API 키 및 엔드포인트 목록을 나타냅니다.
     * </p>
     */
    @Getter
    @Setter
    @ToString
    public static class ApiServer {
        /** 서버 이름 */
        private String name;

        /** 서버 도메인 */
        private String domain;

        /** 서버 API 키 */
        private String key;

        /** 서버의 엔드포인트 목록 */
        private List<Endpoint> endpoints;

        /**
         * 엔드포인트 정보 클래스
         * 
         * <p>
         * 각 엔드포인트의 키와 설명을 나타냅니다.
         * </p>
         */
        @Getter
        @Setter
        @ToString
        public static class Endpoint {
            /** 엔드포인트 키 */
            private String endpointKey;

            /** 엔드포인트 설명 */
            private String description;
        }
    }

    /**
     * 주어진 엔드포인트 키에 해당하는 API 서버의 전체 URL을 반환합니다.
     * 
     * <p>
     * API 서버 목록에서 엔드포인트 키가 일치하는 서버를 찾고, 도메인 끝에 '/'가 없으면 추가합니다. 그런 다음, 도메인과 엔드포인트 키를
     * 결합하여 전체 URL을 반환합니다.
     * </p>
     * 
     * @param endPointKey 엔드포인트 키
     * @return 전체 URL 또는 null (일치하는 엔드포인트가 없을 경우)
     */
    public String getApiProperties(String endPointKey) {
        if (servers == null) {
            return null;
        }

        for (ApiServer server : servers) {
            Optional<ApiServer.Endpoint> endpoint = server.getEndpoints().stream().filter(ep -> ep.getEndpointKey().equalsIgnoreCase(endPointKey)).findFirst();

            // 엔드포인트가 존재할 경우 도메인 끝에 '/'가 없으면 추가하고, 전체 URL을 반환
            if (endpoint.isPresent()) {
                String domain = server.getDomain();

                if (!server.getDomain().endsWith("/")) {
                    domain += "/";
                }
                return domain + endPointKey;
            }
        }
        return null;
    }

    /**
     * 주어진 이름에 해당하는 API 서버의 API 키를 반환합니다.
     * 
     * <p>
     * API 서버 목록에서 이름이 일치하는 서버의 API 키를 반환합니다.
     * </p>
     * 
     * @param name API 서버 이름
     * @return API 키 또는 null (일치하는 서버가 없을 경우)
     */
    public String getApiKey(String name) {
        if (servers == null) {
            return null;
        }

        for (ApiServer server : servers) {

            // 서버 이름이 일치하는 경우 API 키를 반환
            if (server.getName().equalsIgnoreCase(name)) {
                return server.getKey();
            }
        }
        return null;
    }

}
