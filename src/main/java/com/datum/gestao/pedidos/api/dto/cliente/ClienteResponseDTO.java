package com.datum.gestao.pedidos.api.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "ClienteResponseDTO", description = "Resposta completa do cliente")
public record ClienteResponseDTO(

        @Schema(example = "1") Long id,
        @Schema(example = "Misael Borges Cancelier") String nome,
        @Schema(example = "misael.borges@email.com") String email,
        @Schema(example = "85023101002") String cpf,
        @Schema(example = "11987654321") String telefone,
        @Schema(example = "01001000") String cep,
        @Schema(example = "Rua A") String logradouro,
        @Schema(example = "123") String numero,
        @Schema(example = "Apto 2") String complemento,
        @Schema(example = "Centro") String bairro,
        @Schema(example = "São Paulo") String cidade,
        @Schema(example = "SP") String estado,

        @Schema(example = "2025-01-10T10:30:00")
        LocalDateTime dataCadastro
) {}
