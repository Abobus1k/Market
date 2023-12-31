package ru.example.megamarket.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "aobubu",
                        email = "ginkinivan@yandex.ru"
                ),
                description = "OpenApi documentation",
                title = "OpenApi specification - aobubu",
                version = "1.0",
                termsOfService = "FREE TO USE"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8003"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://nedomarket.ru:8003"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
