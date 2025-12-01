package com.datum.gestao.pedidos.api.dto.pedido;

import com.datum.gestao.pedidos.api.dto.itemPedido.ItemPedidoRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record PedidoRequestDTO(

        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @Valid
        @NotEmpty(message = "Pedido deve ter ao menos um item")
        List<ItemPedidoRequestDTO> itens,

        String observacoes
) {}

