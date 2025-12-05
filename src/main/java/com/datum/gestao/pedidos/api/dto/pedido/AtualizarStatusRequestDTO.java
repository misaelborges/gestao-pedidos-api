package com.datum.gestao.pedidos.api.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AtualizarStatusRequestDTO",
        description = "DTO para atualizar o status de um pedido. " +
                "Recomendo usar os valores do enum StatusPedido (ex: AGUARDANDO_PAGAMENTO, " +
                "PAGAMENTO_CONFIRMADO, EM_SEPARACAO, EM_TRANSPORTE, ENTREGUE, CANCELADO).")
public record AtualizarStatusRequestDTO(
        @Schema(example = "PROCESSANDO", description = "Novo status do pedido")
        String status
) {}
