package com.func.functional.configs.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientConfig 클래스는 WebClient의 빈 설정을 제공합니다.
 * 
 * <p>
 * 이 클래스는 WebClient.Builder를 사용하여 WebClient 인스턴스를 생성하고, 
 * 이를 Spring 컨테이너에 빈으로 등록합니다. 
 * 이를 통해 애플리케이션 내에서 WebClient를 주입받아 사용할 수 있습니다.
 * </p>
 */
@Configuration
public class WebClientConfig {

    /**
     * WebClient 빈을 생성합니다.
     * 
     * <p>
     * WebClient.Builder를 사용하여 WebClient 인스턴스를 생성합니다. 
     * 이 빈은 애플리케이션의 다른 구성 요소에서 주입받아 사용할 수 있습니다.
     * </p>
     * 
     * @param builder WebClient.Builder 인스턴스
     * @return WebClient 인스턴스
     */
    @Bean
    WebClient webclient(WebClient.Builder builder) {
	return builder.build();
    }
}
