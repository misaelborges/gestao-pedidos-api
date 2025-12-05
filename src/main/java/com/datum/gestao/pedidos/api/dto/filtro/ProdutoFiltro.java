package com.datum.gestao.pedidos.api.dto.filtro;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "ProdutoFiltro")
public record ProdutoFiltro(
        @Schema(example = "Notebook") String nome,
        @Schema(example = "Informática") String categoria,
        @Schema(example = "1000") BigDecimal precoMin,
        @Schema(example = "5000") BigDecimal precoMax
) {}
