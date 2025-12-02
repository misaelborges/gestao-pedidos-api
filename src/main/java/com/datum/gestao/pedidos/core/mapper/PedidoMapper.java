package com.datum.gestao.pedidos.core.mapper;

import com.datum.gestao.pedidos.api.dto.pedido.PedidoRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResponseDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResumoResponseDTO;
import com.datum.gestao.pedidos.domain.model.Cliente;
import com.datum.gestao.pedidos.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    Pedido toPedido(PedidoRequestDTO pedidoRequestDTO);

    @Mapping(source = "cliente.nome", target = "cliente")
    PedidoResponseDTO toPedidoResponseDTO(Pedido pedido);

    List<PedidoResumoResponseDTO> toPedidoResumoDTO(List<Pedido> pedidos);

    default String map(Cliente cliente) {
        return cliente != null ? cliente.getNome() : null;
    }
}
