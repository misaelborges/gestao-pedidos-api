package com.datum.gestao.pedidos.api.assembler;

import com.datum.gestao.pedidos.api.controller.ClienteController;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResponseDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResumoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteAssembler {

    public EntityModel<ClienteResponseDTO> toClienteResponseDTO(ClienteResponseDTO clienteResponseDTO) {
        return EntityModel.of(clienteResponseDTO)
                .add(linkTo(methodOn(ClienteController.class)
                        .buscarClientePorId(clienteResponseDTO.id())).withSelfRel());
    }

    public PagedModel<EntityModel<ClienteResumoResponseDTO>> toClienteResumoDTO(Page<ClienteResumoResponseDTO> dtoList) {
        List<EntityModel<ClienteResumoResponseDTO>> clientesComLink = dtoList.stream()
                .map(dto ->
                        EntityModel.of(dto).add(linkTo(methodOn(ClienteController.class)
                                .buscarClientePorId(dto.id())).withSelfRel()))
                .toList();

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                dtoList.getSize(),
                dtoList.getNumber(),
                dtoList.getTotalElements(),
                dtoList.getTotalPages()
        );

        PagedModel<EntityModel<ClienteResumoResponseDTO>> pagedModel =
                PagedModel.of(clientesComLink, pageMetadata);

        pagedModel.add(linkTo(methodOn(ClienteController.class)
                .listarClientes(dtoList.getPageable())).withSelfRel());

        return pagedModel;
    }
}
