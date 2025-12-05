package com.datum.gestao.pedidos.api.dto.produto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(name = "ProdutoResponseDTO", description = "Resposta completa de produto")
public record ProdutoResponseDTO(
        @Schema(example = "1") Long id,
        @Schema(example = "Notebook Dell") String nome,
        @Schema(example = "Notebook i7") String descricao,
        @Schema(example = "4500.00") BigDecimal preco,
        @Schema(example = "10") Integer estoqueDisponivel,
        @Schema(example = "SKU12345") String sku,
        @Schema(example = "Informática") String categoria,
        @Schema(example = "2025-01-10T10:30:00") LocalDateTime dataCadastro,
        @Schema(example = "2025-01-11T12:00:00") LocalDateTime dataAtualizacao
) {}

