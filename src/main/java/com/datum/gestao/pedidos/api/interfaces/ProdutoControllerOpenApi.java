package com.datum.gestao.pedidos.api.interfaces;

import com.datum.gestao.pedidos.api.dto.produto.ProdutoAtualizaRequestDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoRequestDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResumoResponseDTO;
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

import java.math.BigDecimal;

@Tag(name = "Produtos", description = "Gerenciamento de produtos")
public interface ProdutoControllerOpenApi {

    @Operation(summary = "Buscar produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    ResponseEntity<EntityModel<ProdutoResponseDTO>> buscarProdutoPorId(
            @Parameter(description = "ID do produto", example = "10", required = true) Long id
    );

    @Operation(summary = "Buscar produto por ID ou SKU")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ResponseEntity<EntityModel<ProdutoResponseDTO>> buscarProduto(
            @Parameter(description = "ID do produto", example = "1") Long id,
            @Parameter(description = "SKU do produto", example = "SKU-2025") String sku
    );


    @Operation(summary = "Listar produtos com filtros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de produtos"),
            @ApiResponse(responseCode = "400", description = "Filtros inválidos")
    })
    ResponseEntity<PagedModel<EntityModel<ProdutoResumoResponseDTO>>> listarProdutos(
            @Parameter(description = "Nome do produto") String nome,
            @Parameter(description = "Categoria") String categoria,
            @Parameter(description = "Preço mínimo") BigDecimal precoMin,
            @Parameter(description = "Preço máximo") BigDecimal precoMax,
            @Parameter(hidden = true) Pageable pageable
    );

    @Operation(summary = "Cadastrar novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Produto já cadastrado")
    })
    ResponseEntity<EntityModel<ProdutoResponseDTO>> salvarProduto(
            @RequestBody(description = "Dados do produto", required = true) ProdutoRequestDTO produtoRequestDTO
    );

    @Operation(summary = "Atualizar produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto inexistente"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<EntityModel<ProdutoResponseDTO>> atualizarProdutoPorId(
            @Parameter(description = "ID do produto", example = "2") Long id,
            @RequestBody(description = "Dados atualizados do produto") ProdutoAtualizaRequestDTO produtoAtualizaRequestDTO
    );

    @Operation(summary = "Excluir produto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ResponseEntity<?> deletarProtudoPorId(@Parameter(description = "ID do produto", example = "5") Long id);
}
