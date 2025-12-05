package com.datum.gestao.pedidos.api.dto.itemPedido;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "ItemPedidoRequestDTO", description = "Item do pedido")
public record ItemPedidoRequestDTO(

        @Schema(example = "10")
        @NotNull(message = "Produto é obrigatório")
        Long produtoId,

        @Schema(example = "2")
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        Integer quantidade,

        @Schema(example = "Produtos separados, por favor")
        String observacoes
) {}
