package com.datum.gestao.pedidos.api.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "ClienteAtualizaRequestDTO", description = "DTO para atualização de cliente")
public record ClienteAtualizaRequestDTO(

        @Schema(example = "Maria Silva")
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100)
        String nome,

        @Schema(example = "11988887777")
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(
                regexp = "\\d{10,11}",
                message = "Telefone deve conter DDD + número"
        )
        String telefone,

        @Schema(example = "Av. Paulista")
        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,

        @Schema(example = "1500")
        @NotBlank(message = "Número é obrigatório")
        String numero,

        @Schema(example = "Bloco B")
        String complemento,

        @Schema(example = "Bela Vista")
        @NotBlank(message = "Bairro é obrigatório")
        String bairro,

        @Schema(example = "São Paulo")
        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        @Schema(example = "SP")
        @NotBlank(message = "Estado é obrigatório")
        @Size(min = 2, max = 2)
        String estado

) {}
