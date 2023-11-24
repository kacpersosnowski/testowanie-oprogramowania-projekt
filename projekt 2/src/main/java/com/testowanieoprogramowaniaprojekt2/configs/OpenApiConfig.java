package com.testowanieoprogramowaniaprojekt2.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Testowanie oprogramowania projekt 2")
                        .description("REST API dla aplikacji testowanie oprogramowania.")
                        .version("1.0")
                );
    }

}
