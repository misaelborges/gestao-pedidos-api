package com.datum.gestao.pedidos.api.interfaces;

import com.datum.gestao.pedidos.api.dto.pedido.AtualizarStatusRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResponseDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResumoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Tag(name = "Pedidos", description = "Gerenciamento de pedidos")
public interface PedidoControllerOpenApi {

    @Operation(summary = "Criar pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<EntityModel<PedidoResponseDTO>> criarPedido(
            @RequestBody(required = true) PedidoRequestDTO pedidoRequestDTO
    );

    @Operation(summary = "Buscar pedido por ID ou número")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido inexistente")
    })
    ResponseEntity<EntityModel<PedidoResponseDTO>> buscarPedido(
            @Parameter(description = "ID do pedido") Long pedidoId,
            @Parameter(description = "Número do pedido") String numeroPedido
    );

    @Operation(summary = "Buscar pedido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido inexistente")
    })
    ResponseEntity<EntityModel<PedidoResponseDTO>> buscarPedidoId(@Parameter(description = "ID do pedido") Long id);

    @Operation(summary = "Listar pedidos com filtros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos"),
            @ApiResponse(responseCode = "400", description = "Filtros inválidos")
    })
    ResponseEntity<PagedModel<EntityModel<PedidoResumoResponseDTO>>> buscarComFiltros(
            @Parameter(description = "Status do pedido") String status,
            @Parameter(description = "Cliente") String cliente,
            @Parameter(description = "Data e Hora") LocalDateTime data,
            @Parameter(hidden = true) Pageable pageable
    );

    @Operation(summary = "Avançar status do pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado"),
            @ApiResponse(responseCode = "400", description = "Status inválido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    ResponseEntity<PedidoResponseDTO> avancarStatus(@Parameter(description = "ID do pedido") Long id
    );

    @Operation(summary = "Atualizar status manualmente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado"),
            @ApiResponse(responseCode = "400", description = "Status inválido")
    })
    ResponseEntity<PedidoResponseDTO> avancarStatusPedido(
            @Parameter(description = "ID do pedido") Long id,
            @RequestBody AtualizarStatusRequestDTO request
    );

    @Operation(summary = "Cancelar pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido cancelado"),
            @ApiResponse(responseCode = "404", description = "Pedido inexistente")
    })
    ResponseEntity<?> cancelarPedido(@Parameter(description = "ID do pedido") Long id);
}

