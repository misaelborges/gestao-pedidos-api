package com.datum.gestao.pedidos.api.controller;

import com.datum.gestao.pedidos.api.assembler.ProdutoAssembler;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResumoResponseDTO;
import com.datum.gestao.pedidos.domain.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoAssembler produtoAssembler;

    public ProdutoController(ProdutoService produtoService, ProdutoAssembler produtoAssembler) {
        this.produtoService = produtoService;
        this.produtoAssembler = produtoAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ProdutoResumoResponseDTO>>> listarProdutos(Pageable pageable) {
        Page<ProdutoResumoResponseDTO> produtoResumoResponseDTOS = produtoService.listarProdutos(pageable);
        PagedModel<EntityModel<ProdutoResumoResponseDTO>> produtoResumoDTO = produtoAssembler.toProdutoResumoDTO(produtoResumoResponseDTOS);
        return ResponseEntity.status(HttpStatus.OK).body(produtoResumoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.buscarProdutoPorId(id);
        EntityModel<ProdutoResponseDTO> protudoResponseDTO = produtoAssembler.toProtudoResponseDTO(produtoResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(protudoResponseDTO);
    }
}
