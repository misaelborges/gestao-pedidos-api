package com.datum.gestao.pedidos.api.controller;

import com.datum.gestao.pedidos.api.assembler.ProdutoAssembler;
import com.datum.gestao.pedidos.api.dto.filtro.ProdutoFiltro;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoAtualizaRequestDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoRequestDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResumoResponseDTO;
import com.datum.gestao.pedidos.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoAssembler produtoAssembler;

    public ProdutoController(ProdutoService produtoService, ProdutoAssembler produtoAssembler) {
        this.produtoService = produtoService;
        this.produtoAssembler = produtoAssembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.buscarProdutoPorId(id);
        EntityModel<ProdutoResponseDTO> protudoResponseDTO = produtoAssembler.toProdutoResponseDTO(produtoResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(protudoResponseDTO);
    }

    @GetMapping("/buscar")
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> buscarProduto(@RequestParam(required = false) Long id,
                                                                         @RequestParam(required = false) String sku) {

        ProdutoResponseDTO produtoResponseDTO = produtoService.buscarProdutoSkuId(id, sku);
        EntityModel<ProdutoResponseDTO> dtoEntityModel = produtoAssembler.toProdutoResponseDTO(produtoResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(dtoEntityModel);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ProdutoResumoResponseDTO>>> listarProdutos(
            @RequestParam(required = false) String nome, @RequestParam(required = false) String categoria,
            @RequestParam(required = false) BigDecimal precoMin, @RequestParam(required = false) BigDecimal precoMax,
            Pageable pageable) {

        ProdutoFiltro filtro = new ProdutoFiltro(nome, categoria, precoMin, precoMax);

        Page<ProdutoResumoResponseDTO> produtos = produtoService.buscarComFiltros(filtro, pageable);
        PagedModel<EntityModel<ProdutoResumoResponseDTO>> produtoResumoDTO = produtoAssembler.toProdutoResumoDTO(produtos, filtro);
        return ResponseEntity.status(HttpStatus.OK).body(produtoResumoDTO);
    }

    @PostMapping
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> salvarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.salvarProduto(produtoRequestDTO);
        EntityModel<ProdutoResponseDTO> protudoResponseDTO = produtoAssembler.toProdutoResponseDTO(produtoResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(protudoResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> atualizarProdutoPorId(@PathVariable Long id,
                                              @RequestBody @Valid ProdutoAtualizaRequestDTO produtoAtualizaRequestDTO) {

        ProdutoResponseDTO produtoResponseDTO = produtoService.atualizarProdutoPorId(id, produtoAtualizaRequestDTO);
        EntityModel<ProdutoResponseDTO> protudoResponseDTO = produtoAssembler.toProdutoResponseDTO(produtoResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(protudoResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProtudoPorId(@PathVariable Long id) {
        produtoService.deletarProtudoPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
