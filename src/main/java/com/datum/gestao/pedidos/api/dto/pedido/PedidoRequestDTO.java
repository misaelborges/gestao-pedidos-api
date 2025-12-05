package com.datum.gestao.pedidos.api.dto.pedido;

import com.datum.gestao.pedidos.api.dto.itemPedido.ItemPedidoRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@Schema(name = "PedidoRequestDTO", description = "Cadastro de pedido")
public record PedidoRequestDTO(

        @Schema(example = "1", description = "ID do cliente")
        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @Schema(description = "Lista de itens do pedido")
        @Valid
        @NotEmpty(message = "Pedido deve ter ao menos um item")
        List<ItemPedidoRequestDTO> itens,

        @Schema(example = "Entregar no período da manhã")
        String observacoes
) {}

