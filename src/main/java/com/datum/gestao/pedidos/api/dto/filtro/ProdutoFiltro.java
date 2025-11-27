package com.datum.gestao.pedidos.api.dto.filtro;

import java.math.BigDecimal;

public record ProdutoFiltro(
        String nome,
        String categoria,
        BigDecimal precoMin,
        BigDecimal precoMax
) {}
