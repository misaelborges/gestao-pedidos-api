package com.datum.gestao.pedidos.api.dto.filtro;

import java.time.LocalDateTime;

public record PedidoFiltro(
        String nome,
        String status,
        String cliente,
        LocalDateTime data
) {}
