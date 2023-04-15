/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author moabdu
 */

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Base Server URL")})
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String title;
    private static final String DESCRIPTION = """
            Online bookstore application provides a convenient and secure platform for users to search for and purchase
            books online. The application offers a wide selection of books, and its intuitive interface makes it easy for
            users to find what they are looking for quickly.
            """;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(this.getInfo())
                .components(new Components().addSecuritySchemes("basicAuth", securityScheme()));
    }

    @Bean
    public SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic");
    }

    private Contact myContactInfo() {
        Contact contact = new Contact();
        contact.setName("Mohammed Abdu");
        contact.setEmail("eng.mo.abdu@gmail.com");
        contact.setUrl("https://www.linkedin.com/in/engmoabdu");
        return contact;
    }

    private Info getInfo() {
        return new Info()
                .title(title)
                .contact(myContactInfo())
                .version("0.1")
                .description(DESCRIPTION);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
                registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
            }
        };
    }
}
