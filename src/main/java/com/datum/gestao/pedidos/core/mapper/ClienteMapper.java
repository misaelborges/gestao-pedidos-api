package com.datum.gestao.pedidos.core.mapper;

import com.datum.gestao.pedidos.api.dto.cliente.ClienteRequestDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResumoResponseDTO;
import com.datum.gestao.pedidos.domain.model.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toCliente(ClienteRequestDTO clientRequestDTO);
    List<ClienteResumoResponseDTO> toClienteResumoDTO(List<Cliente> clientes);
}
