package com.datum.gestao.pedidos.api.assembler;

import com.datum.gestao.pedidos.api.controller.ProdutoController;
import com.datum.gestao.pedidos.api.dto.filtro.ProdutoFiltro;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResumoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProdutoAssembler {

    public EntityModel<ProdutoResponseDTO> toProdutoResponseDTO(ProdutoResponseDTO produtoResponseDTO) {
        return EntityModel.of(produtoResponseDTO)
                .add(linkTo(methodOn(ProdutoController.class)
                        .buscarProdutoPorId(produtoResponseDTO.id())).withSelfRel());
    }

    public PagedModel<EntityModel<ProdutoResumoResponseDTO>> toProdutoResumoDTO(Page<ProdutoResumoResponseDTO> dtoList,
                                                                                ProdutoFiltro filtro) {
        List<EntityModel<ProdutoResumoResponseDTO>> produtosComLink = dtoList.stream()
                .map(dto ->
                        EntityModel.of(dto).add(linkTo(methodOn(ProdutoController.class)
                                .buscarProdutoPorId(dto.id())).withSelfRel()))
                .toList();

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                dtoList.getSize(),
                dtoList.getNumber(),
                dtoList.getTotalElements(),
                dtoList.getTotalPages()
        );

        PagedModel<EntityModel<ProdutoResumoResponseDTO>> pagedModel =
                PagedModel.of(produtosComLink, pageMetadata);

        pagedModel.add(linkTo(methodOn(ProdutoController.class)
                .listarProdutos(
                        filtro.nome(),
                        filtro.categoria(),
                        filtro.precoMin(),
                        filtro.precoMax(),
                        dtoList.getPageable()))
                .withSelfRel());

        return pagedModel;
    }
}
