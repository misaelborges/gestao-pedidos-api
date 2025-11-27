package com.datum.gestao.pedidos.api.controller;

import com.datum.gestao.pedidos.api.assembler.ClienteAssembler;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteAtualizaRequestDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteRequestDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResponseDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResumoResponseDTO;
import com.datum.gestao.pedidos.domain.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteAssembler clienteAssembler;

    public ClienteController(ClienteService clienteService, ClienteAssembler clienteAssembler) {
        this.clienteService = clienteService;
        this.clienteAssembler = clienteAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ClienteResumoResponseDTO>>> listarClientes(Pageable pageable) {
        Page<ClienteResumoResponseDTO> clientePage = clienteService.listarClientes(pageable);
        PagedModel<EntityModel<ClienteResumoResponseDTO>> clienteResumoDTO = clienteAssembler.toClienteResumoDTO(clientePage);
        return ResponseEntity.status(HttpStatus.OK).body(clienteResumoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteResponseDTO>> buscarClientePorId(@PathVariable Long id) {
        ClienteResponseDTO clienteResponseDTO = clienteService.buscarClientePorId(id);
        EntityModel<ClienteResponseDTO> clienteComLink = clienteAssembler.toClienteResponseDTO(clienteResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(clienteComLink);
    }

    @PostMapping
    public ResponseEntity<EntityModel<ClienteResponseDTO>> salvarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteResponseDTO = clienteService.salvarCliente(clienteRequestDTO);
        EntityModel<ClienteResponseDTO> clienteComLink = clienteAssembler.toClienteResponseDTO(clienteResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteComLink);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteResponseDTO>> atualizarClientePorId(
                       @PathVariable Long id, @RequestBody @Valid ClienteAtualizaRequestDTO clienteAtualizaRequestDTO) {

        ClienteResponseDTO clienteResponseDTO = clienteService.atualizarClientePorId(id, clienteAtualizaRequestDTO);
        EntityModel<ClienteResponseDTO> clienteComLink = clienteAssembler.toClienteResponseDTO(clienteResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(clienteComLink);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarClientePorId(@PathVariable Long id) {
        clienteService.deletarClientePorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
