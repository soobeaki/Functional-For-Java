package com.func.functional.configs.springdoc;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.func.functional.http.model.BaseHeader;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;

/**
 * SpringDocConfig 클래스는 SpringDoc의 OpenAPI 문서 생성을 구성합니다.
 * 
 * <p>
 * 이 클래스는 OpenAPI의 설정과 API 문서화의 사용자 정의를 제공합니다. SpringDoc을 통해 API 문서를 생성하고,
 * Swagger UI에서 사용할 수 있도록 설정합니다.
 * </p>
 */
@Configuration
public class SpringDocConfig {

    /** 현재 활성화된 프로파일 */
    @Value("${spring.profiles.active}")
    private String activeProfile;

    /** API 버전 */
    @Value("${api-version}")
    private String apiVersion;

    /**
     * OpenAPI 문서의 기본 정보를 설정합니다.
     * 
     * @return API 문서에 표시될 정보
     */
    private Info apiInfo() {
        return new Info().title("FUNC::APP - " + activeProfile).description("Functional :: APP - API 명세를 제공한다.").version(apiVersion);
    }

    /**
     * 헤더 파라미터 객체를 생성합니다.
     * 
     * @param name        파라미터 이름
     * @param description 파라미터 설명
     * @param isRequired  필수 여부
     * @param example     예시 값
     * @return 생성된 Parameter 객체
     */
    private Parameter createHeaderParam(String name, String description, Boolean isRequired, String example) {
        return new Parameter()
                .in("header")
                .name(name)
                .description(description)
                .required(isRequired)
                .example(example)
                .schema(new StringSchema());
    }

    /**
     * OpenAPI 문서의 기본 헤더 파라미터를 추가합니다.
     * 
     * @return OpenApiCustomizer 인스턴스
     */
    @Bean
    OpenApiCustomizer baseHeader() {
        Parameter userId = createHeaderParam(BaseHeader.USER_ID, "사용자ID", false, "500000001");
        Parameter role = createHeaderParam(BaseHeader.ROLE, "역할", false, "ROLE_ADMIN");
        Parameter cryptoApply = createHeaderParam(BaseHeader.CRYPTO_APPLY, "암/복호화 key", false, "4885");
        Parameter timestamp = createHeaderParam(BaseHeader.TIMESTAMP, "long type 현재시간", false, "1643234567456234");

        return openApi -> openApi.getPaths().values()
                .forEach(operation -> operation
                        .addParametersItem(userId)
                        .addParametersItem(role)
                        .addParametersItem(cryptoApply)
                        .addParametersItem(timestamp));
    }

    /**
     * OpenAPI 빈을 설정합니다.
     * 
     * @return OpenAPI 인스턴스
     */
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(this.apiInfo());
    }

    /**
     * 'local' 및 'dev' 프로파일에서 사용할 GroupedOpenApi 빈을 설정합니다.
     * 
     * @return GroupedOpenApi 인스턴스
     */
    @Bean
    @Profile(value = { "local", "dev" })
    GroupedOpenApi functional() {
        return GroupedOpenApi.builder()
                .group("Functional")
                .pathsToMatch("/" + apiVersion + "/functional/**")
                .addOpenApiCustomizer(this.baseHeader())
                .build();
    }
}
