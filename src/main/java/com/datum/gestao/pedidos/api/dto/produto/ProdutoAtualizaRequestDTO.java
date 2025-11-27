package com.datum.gestao.pedidos.api.dto.produto;

import java.math.BigDecimal;

public record ProdutoAtualizaRequestDTO(
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoqueDisponivel,
        String categoria
) {}
