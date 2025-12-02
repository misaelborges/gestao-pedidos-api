package com.datum.gestao.pedidos.api.controller;

import com.datum.gestao.pedidos.api.dto.filtro.PedidoFiltro;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResponseDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResumoResponseDTO;
import com.datum.gestao.pedidos.domain.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody @Valid PedidoRequestDTO pedidoRequestDTO) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.criarPedido(pedidoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponseDTO);
    }

    @GetMapping("/buscar")
    public ResponseEntity<PedidoResponseDTO> buscarPedido(@RequestParam(required = false) Long pedidoId,
                                                          @RequestParam(required = false) String numeroPedido) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.buscarPedido(pedidoId, numeroPedido);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoId(@PathVariable Long id) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.buscarPedidoId(id);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResumoResponseDTO>> buscarComFiltros(
            @RequestParam(required = false) String nome, @RequestParam(required = false) String status,
            @RequestParam(required = false) String cliente, @RequestParam(required = false) LocalDateTime data,
            Pageable pageable) {

        PedidoFiltro filtro = new PedidoFiltro(nome, status, cliente, data);
        Page<PedidoResumoResponseDTO> pedidoResumoResponseDTOS = pedidoService.buscarComFiltros(filtro, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(pedidoResumoResponseDTOS);
    }
}
