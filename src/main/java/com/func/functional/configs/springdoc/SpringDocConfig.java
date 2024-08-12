package com.func.functional.configs.springdoc;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.Value;

@Configuration
public class SpringDocConfig {

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Value("${api-version}")
	private String apiVersion;

	private Info info() {
		return new Info().title("FUNC::APP - " + activeProfile).description("Functional :: APP - API 명세를 제공한다.")
				.version(apiVersion);
	}
	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().components(null)
	}
	
}
