package com.datum.gestao.pedidos.api.dto.itemPedido;

import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "ItemValidadoDTO", description = "Item do pedido com produto detalhado, quantidade e subtotal calculado.")
public record ItemValidadoDTO(
        @Schema(description = "Produto detalhado")
        ProdutoResponseDTO produto,

        @Schema(example = "2", description = "Quantidade solicitada deste produto")
        Integer quantidade,

        @Schema(example = "149.90", description = "Subtotal (preço do produto multiplicado pela quantidade)")
        BigDecimal subtotal
) {}
