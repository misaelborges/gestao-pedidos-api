package com.datum.gestao.pedidos.core.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("API - Gestão de Pedidos")
                        .version("1.0.0")
                        .description("""
                                API REST para gerenciamento de:
                                - Clientes
                                - Produtos
                                - Pedidos
                                - Atualização de status
                                - Cancelamento de pedidos
                                
                                Desenvolvida em Java com Spring Boot.
                                """)
                        .contact(new Contact()
                                .name("Misael Borges Cancelier")
                                .email("mizaelborges2011@email.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT"))
                )
                .components(new Components()
                        .addResponses("BadRequest", new ApiResponse().description("Requisição inválida"))
                        .addResponses("NotFound", new ApiResponse().description("Recurso não encontrado"))
                        .addResponses("Conflict", new ApiResponse().description("Conflito de dados"))
                        .addResponses("Unauthorized", new ApiResponse().description("Acesso não autorizado"))
                        .addResponses("Forbidden", new ApiResponse().description("Acesso proibido"))
                        .addResponses("InternalServerError", new ApiResponse().description("Erro interno do servidor"))
                );
    }
}
