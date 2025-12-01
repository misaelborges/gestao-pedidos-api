package com.datum.gestao.pedidos.core.mapper;

import com.datum.gestao.pedidos.api.dto.pedido.PedidoRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResponseDTO;
import com.datum.gestao.pedidos.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    Pedido toPedido(PedidoRequestDTO pedidoRequestDTO);

    @Mapping(source = "cliente.nome", target = "cliente")
    PedidoResponseDTO toPedidoResponseDTO(Pedido pedido);
}
