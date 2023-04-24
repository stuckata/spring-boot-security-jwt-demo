package com.demo.springbootsecurityjwtdemo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "${api.title}",
                version = "${api.version}",
                contact = @Contact(
                        name = "${api.contact.name}",
                        email = "${api.contact.email}",
                        url = "${api.contact.url}"
                ),
                license = @License(
                        name = "${api.license.name}",
                        url = "${api.license.url}"
                ),
                termsOfService = "${api.tos.uri}",
                description = "${api.description}"),
        servers = {
                @Server(
                        url = "http://localhost:9090",
                        description = "Development"
                ),
                @Server(
                        url = "${api.server.url}",
                        description = "${api.stage}"
                )})
public class OpenAPI30Configuration {

    private static final String SECURITY_SCHEME = "bearerAuth";

    /**
     * Configure the OpenAPI components.
     *
     * @return Returns fully configure OpenAPI object
     * @see OpenAPI
     */
    @Bean
    public OpenAPI customizeOpenAPI() {

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME, new SecurityScheme()
                                .name(SECURITY_SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .description(
                                        "Provide the JWT token. JWT token can be obtained from the Authentication API.")
                                .bearerFormat("JWT")));
    }
}