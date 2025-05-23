package com.bastos.ngbillings.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("ng-billings API")
                        .description("Desafio ng-billings")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Israel Bastos")
                                .email("israelbastos@hotmail.com")
                        )
                );
    }
}
