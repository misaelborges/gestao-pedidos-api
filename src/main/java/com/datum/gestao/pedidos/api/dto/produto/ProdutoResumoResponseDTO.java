package com.datum.gestao.pedidos.api.dto.produto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "ProdutoResponseDTO", description = "Resumo de produto")
public record ProdutoResumoResponseDTO(
        @Schema(example = "1") Long id,
        @Schema(example = "Notebook Dell") String nome,
        @Schema(example = "4500.00") BigDecimal preco,
        @Schema(example = "10") Integer estoqueDisponivel,
        @Schema(example = "Informática") String categoria
) {
}
