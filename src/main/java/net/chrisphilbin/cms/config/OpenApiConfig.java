package net.chrisphilbin.cms.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
            .title("A CMS API Backend Using Java Spring Boot")
            .description("An API that can manage blog posts")
            .version("v1.0"));
    }
}