package com.datum.gestao.pedidos.api.dto.itemPedido;

import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;

import java.math.BigDecimal;

public record ItemValidadoDTO(
        ProdutoResponseDTO produto,
        Integer quantidade,
        BigDecimal subtotal
) {}
