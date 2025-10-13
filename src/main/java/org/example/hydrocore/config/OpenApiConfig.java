package org.example.hydrocore.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "HydroCore API",
                version = "v1",
                description = "Documentação da API do HydroCore."
        )
)
@SecurityScheme(
        name = "bearerAuth", // Nome do esquema de segurança usado nas requisições
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Insira o token JWT no formato: Bearer [token]"
)
@SecurityScheme(
        name = "userEmailHeader",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-User-Email",
        description = "Email do usuário logado."
)
public class OpenApiConfig {
}