package com.datum.gestao.pedidos.api.controller;

import com.datum.gestao.pedidos.api.assembler.ProdutoAssembler;
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

    @PostMapping
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> salvarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.salvarProduto(produtoRequestDTO);
        EntityModel<ProdutoResponseDTO> protudoResponseDTO = produtoAssembler.toProtudoResponseDTO(produtoResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(protudoResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> atualizarProdutoPorId(@PathVariable Long id,
                                              @RequestBody @Valid ProdutoAtualizaRequestDTO produtoAtualizaRequestDTO) {

        ProdutoResponseDTO produtoResponseDTO = produtoService.atualizarProdutoPorId(id, produtoAtualizaRequestDTO);
        EntityModel<ProdutoResponseDTO> protudoResponseDTO = produtoAssembler.toProtudoResponseDTO(produtoResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(protudoResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProtudoPorId(@PathVariable Long id) {
        produtoService.deletarProtudoPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
