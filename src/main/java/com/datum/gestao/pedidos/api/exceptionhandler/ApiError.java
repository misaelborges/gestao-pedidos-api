package com.datum.gestao.pedidos.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(name = "Problema", description = "Representação de um problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiError {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "2025-07-16T15:23:12.798Z")
    private OffsetDateTime timestamp;

    @Schema(example = "https://algafood.com.br/dados-invalidos")
    private String type;

    @Schema(example = "Dados inválidos")
    private String title;

    @Schema(example = "Um ou mais campos estão inválidos. Corrija e tente novamente.")
    private String detail;

    @Schema(example = "Um ou mais campos estão inválidos. Corrija e tente novamente.")
    private String userMessage;
    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {

        private String name;
        private String userMessage;
    }
}
