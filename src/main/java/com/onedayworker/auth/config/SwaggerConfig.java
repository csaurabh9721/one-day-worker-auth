package com.onedayworker.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("OneDay Worker - Auth Service")
                        .description("Authentication Microservice APIs")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Saurabh Chauhan")
                                .email("csaurabh002@gmail.com")));
    }
}
