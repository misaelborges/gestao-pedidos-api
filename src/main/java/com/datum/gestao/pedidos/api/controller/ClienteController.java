package com.datum.gestao.pedidos.api.controller;

import com.datum.gestao.pedidos.api.dto.cliente.ClienteResponseDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResumoResponseDTO;
import com.datum.gestao.pedidos.domain.service.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResumoResponseDTO>> listarClientes(Pageable pageable) {
        Page<ClienteResumoResponseDTO> clientePage = clienteService.listarClientes(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(clientePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Long id) {
        ClienteResponseDTO clienteResponseDTO = clienteService.buscarClientePorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(clienteResponseDTO);
    }
}
