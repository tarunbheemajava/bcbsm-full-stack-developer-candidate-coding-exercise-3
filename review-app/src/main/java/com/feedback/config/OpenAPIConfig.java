package com.feedback.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Review feedback API")
                        .description("Review feedback application")
                        .version("v0.0.1")
                        .license(new License().name("review 1.0").url("https://review.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Review MVP Documentation")
                        .url("https://review.com/"));
    }
}
