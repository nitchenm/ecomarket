package com.acopl.microservice_branch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Microservice Branch API")
                .version("1.0.0")
                .description("Documentación de la API para el microservicio de sucursales (branch).")
            );
    }
}
