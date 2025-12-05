package com.datum.gestao.pedidos.api.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClienteResumoResponseDTO", description = "Resumo do cliente")
public record ClienteResumoResponseDTO(
        @Schema(example = "1") Long id,
        @Schema(example = "Maria Silva") String nome,
        @Schema(example = "misael.borges@email.com") String email,
        @Schema(example = "11988887777") String telefone,
        @Schema(example = "SP") String estado,
        @Schema(example = "São Paulo") String cidade
) {}