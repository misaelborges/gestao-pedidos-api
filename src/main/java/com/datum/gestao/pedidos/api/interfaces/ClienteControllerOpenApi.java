package com.datum.gestao.pedidos.api.interfaces;

import com.datum.gestao.pedidos.api.dto.cliente.ClienteAtualizaRequestDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteRequestDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResponseDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResumoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public interface ClienteControllerOpenApi {

    @Operation(summary = "Listar clientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes")
    })
    ResponseEntity<PagedModel<EntityModel<ClienteResumoResponseDTO>>> listarClientes(
            @Parameter(hidden = true) Pageable pageable
    );

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente inexistente")
    })
    ResponseEntity<EntityModel<ClienteResponseDTO>> buscarClientePorId(@Parameter(description = "ID do cliente") Long id);

    @Operation(summary = "Cadastrar cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<EntityModel<ClienteResponseDTO>> salvarCliente(@RequestBody ClienteRequestDTO clienteRequestDTO);

    @Operation(summary = "Atualizar cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado"),
            @ApiResponse(responseCode = "404", description = "Cliente inexistente")
    })
    ResponseEntity<EntityModel<ClienteResponseDTO>> atualizarClientePorId(
            @Parameter(description = "ID do cliente") Long id,
            @RequestBody ClienteAtualizaRequestDTO clienteAtualizaRequestDTO
    );

    @Operation(summary = "Excluir cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente removido"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    ResponseEntity<?> deletarClientePorId(@Parameter(description = "ID do cliente") Long id);
}

