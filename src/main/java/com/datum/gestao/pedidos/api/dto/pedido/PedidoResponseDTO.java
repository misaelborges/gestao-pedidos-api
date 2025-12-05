package com.datum.gestao.pedidos.api.dto.pedido;

import com.datum.gestao.pedidos.domain.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(name = "PedidoResponseDTO", description = "Resposta do pedido")
public record PedidoResponseDTO(

        @Schema(example = "1") Long id,
        @Schema(example = "PED-2025-0001") String numeroPedido,
        @Schema(example = "João Silva") String cliente,
        @Schema(example = "AGUARDANDO_PAGAMENTO") StatusPedido statusPedido,
        @Schema(example = "250.00") BigDecimal valorTotal,
        @Schema(example = "2025-01-10T10:00:00") LocalDateTime dataPedido,
        @Schema(example = "2025-01-10T10:30:00") LocalDateTime dataAtualizaçao,
        @Schema(example = "Entregar até às 18h") String observacoes
) {}