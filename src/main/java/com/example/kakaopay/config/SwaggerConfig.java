package com.example.kakaopay.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("swagger test")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public OpenAPI swaggerUiSetting() {
        return new OpenAPI()
                .info(new Info()
                        .title("카카오페이 코딩 과제 API SWAGGER DOCS")
                        .description("카카오페이 멤버십 서비스 API LIST")
                        .version("v1.0.0")
                );
    }
}
