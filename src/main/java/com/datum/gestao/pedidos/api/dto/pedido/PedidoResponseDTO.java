package com.datum.gestao.pedidos.api.dto.pedido;

import com.datum.gestao.pedidos.domain.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponseDTO(
        Long id,
        String numeroPedido,
        String cliente,
        StatusPedido statusPedido,
        BigDecimal valorTotal,
        LocalDateTime dataPedido,
        LocalDateTime dataAtualizaçao,
        String observacoes) {
}
