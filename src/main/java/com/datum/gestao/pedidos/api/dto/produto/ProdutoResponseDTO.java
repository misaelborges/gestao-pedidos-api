package com.datum.gestao.pedidos.api.dto.produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoqueDisponivel,
        String sku,
        String categoria,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao)
{}
