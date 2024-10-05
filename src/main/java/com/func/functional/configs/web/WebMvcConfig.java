package com.func.functional.configs.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig 클래스는 Spring MVC에서 CORS(Cross-Origin Resource Sharing) 설정을 구성합니다.
 * 
 * <p>
 * 이 클래스는 특정 경로에 대해 허용할 출처, 헤더, 메서드 등을 정의하여
 * 외부 도메인에서의 API 접근을 제어할 수 있도록 합니다.
 * </p>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    /**
     * CORS 설정을 추가합니다.
     * 
     * @param registry CorsRegistry 인스턴스
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) { 
        registry.addMapping("/**")
                .allowedOriginPatterns("*")                                         // 허용할 출처
                .allowedHeaders("*")                                         // 허용할 헤더
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")   // 허용할 메서드
                .allowCredentials(true);                                     // 자격 증명 허용 여부
    }

}
