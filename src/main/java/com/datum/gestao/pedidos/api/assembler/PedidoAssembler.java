package com.datum.gestao.pedidos.api.assembler;

import com.datum.gestao.pedidos.api.controller.PedidoController;
import com.datum.gestao.pedidos.api.dto.filtro.PedidoFiltro;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResponseDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResumoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoAssembler {

    public EntityModel<PedidoResponseDTO> toPedidoResponseDTO(PedidoResponseDTO pedidoResponseDTO) {
        return EntityModel.of(pedidoResponseDTO)
                .add(linkTo(methodOn(PedidoController.class)
                        .buscarPedidoId(pedidoResponseDTO.id())).withSelfRel());
    }

    public PagedModel<EntityModel<PedidoResumoResponseDTO>> toProdutoResumoDTO(Page<PedidoResumoResponseDTO> dtoList,
                                                                                PedidoFiltro filtro) {
        List<EntityModel<PedidoResumoResponseDTO>> pedidosComLink = dtoList.stream()
                .map(dto ->
                        EntityModel.of(dto).add(linkTo(methodOn(PedidoController.class)
                                .buscarPedidoId(dto.id())).withSelfRel()))
                .toList();

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                dtoList.getSize(),
                dtoList.getNumber(),
                dtoList.getTotalElements(),
                dtoList.getTotalPages()
        );

        PagedModel<EntityModel<PedidoResumoResponseDTO>> pagedModel =
                PagedModel.of(pedidosComLink, pageMetadata);

        pagedModel.add(linkTo(methodOn(PedidoController.class)
                .buscarComFiltros(
                        filtro.status(),
                        filtro.cliente(),
                        filtro.data(),
                        dtoList.getPageable()))
                .withSelfRel());

        return pagedModel;
    }
}
