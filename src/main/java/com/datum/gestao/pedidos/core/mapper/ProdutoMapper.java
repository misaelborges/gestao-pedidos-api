package com.datum.gestao.pedidos.core.mapper;

import com.datum.gestao.pedidos.api.dto.produto.ProdutoRequestDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResumoResponseDTO;
import com.datum.gestao.pedidos.domain.model.Produto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toProduto(ProdutoRequestDTO produtoRequest);
    Produto toProduto(ProdutoResponseDTO produtoResponseDTO);
    List<ProdutoResumoResponseDTO> toProdutoResumoDTO(List<Produto> produtos);
    ProdutoResponseDTO toProdutoResponseDTO(Produto produto);
}
