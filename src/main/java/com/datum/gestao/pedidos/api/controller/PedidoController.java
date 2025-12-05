package com.datum.gestao.pedidos.api.controller;

import com.datum.gestao.pedidos.api.assembler.PedidoAssembler;
import com.datum.gestao.pedidos.api.dto.filtro.PedidoFiltro;
import com.datum.gestao.pedidos.api.dto.pedido.AtualizarStatusRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResponseDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResumoResponseDTO;
import com.datum.gestao.pedidos.domain.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoAssembler pedidoAssembler;

    public PedidoController(PedidoService pedidoService, PedidoAssembler pedidoAssembler) {
        this.pedidoService = pedidoService;
        this.pedidoAssembler = pedidoAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<PedidoResponseDTO>> criarPedido(@RequestBody @Valid PedidoRequestDTO pedidoRequestDTO) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.criarPedido(pedidoRequestDTO);
        EntityModel<PedidoResponseDTO> pedidoResponseDTO1 = pedidoAssembler.toPedidoResponseDTO(pedidoResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponseDTO1);
    }

    @GetMapping("/buscar")
    public ResponseEntity<EntityModel<PedidoResponseDTO>> buscarPedido(@RequestParam(required = false) Long pedidoId,
                                                          @RequestParam(required = false) String numeroPedido) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.buscarPedidoIdNumeroPedido(pedidoId, numeroPedido);
        EntityModel<PedidoResponseDTO> pageResponseDTO = pedidoAssembler.toPedidoResponseDTO(pedidoResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PedidoResponseDTO>> buscarPedidoId(@PathVariable Long id) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.buscarPedidoId(id);
        EntityModel<PedidoResponseDTO> pageResponseDTO = pedidoAssembler.toPedidoResponseDTO(pedidoResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDTO);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PedidoResumoResponseDTO>>> buscarComFiltros(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String cliente, @RequestParam(required = false) LocalDateTime data,
            Pageable pageable) {

        PedidoFiltro filtro = new PedidoFiltro(status, cliente, data);
        Page<PedidoResumoResponseDTO> pedidoResumoResponseDTOS = pedidoService.buscarComFiltros(filtro, pageable);
        PagedModel<EntityModel<PedidoResumoResponseDTO>> produtoResumoDTO = pedidoAssembler.toProdutoResumoDTO(pedidoResumoResponseDTOS, filtro);
        return ResponseEntity.status(HttpStatus.OK).body(produtoResumoDTO);
    }

    @PatchMapping("/{id}/avancar-status")
    public ResponseEntity<PedidoResponseDTO> avancarStatus(@PathVariable Long id) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.atualizarPedido(id);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoResponseDTO);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> avancarStatusPedido(@PathVariable Long id, @RequestBody AtualizarStatusRequestDTO  request) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.atualizarPedido(id, request.status());
        return ResponseEntity.status(HttpStatus.OK).body(pedidoResponseDTO);
    }

    @DeleteMapping("/{id}/cancelar-pedido")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
