package com.datum.gestao.pedidos.api.dto.produto;

import java.math.BigDecimal;

public record ProdutoResumoResponseDTO(
        Long id,
        String nome,
        BigDecimal preco,
        Integer estoqueDisponivel,
        String categoria
) {
}
