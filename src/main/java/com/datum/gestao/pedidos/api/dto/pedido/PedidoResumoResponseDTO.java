package com.datum.gestao.pedidos.api.dto.pedido;

import com.datum.gestao.pedidos.domain.model.StatusPedido;

import java.time.LocalDateTime;

public record PedidoResumoResponseDTO(
        Long id,
        String numeroPedido,
        String cliente,
        StatusPedido statusPedido,
        LocalDateTime dataPedido,
        LocalDateTime dataAtualizaçao
) {
}
