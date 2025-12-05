package com.datum.gestao.pedidos.api.dto.filtro;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "PedidoFiltro")
public record PedidoFiltro(
        @Schema(example = "AGUARDANDO_PAGAMENTO") String status,
        @Schema(example = "João") String cliente,
        @Schema(example = "2025-01-10T10:00:00") LocalDateTime data
) {}
