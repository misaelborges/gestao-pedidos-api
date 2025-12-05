package com.datum.gestao.pedidos.api.dto.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Schema(name = "ProdutoRequestDTO", description = "Cadastro de produto")
public record ProdutoRequestDTO(

        @Schema(example = "Notebook Dell")
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Schema(example = "Notebook i7 16GB RAM")
        String descricao,

        @Schema(example = "4500.00")
        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        BigDecimal preco,

        @Schema(example = "10")
        @NotNull(message = "Estoque é obrigatório")
        @PositiveOrZero(message = "Estoque não pode ser negativo")
        Integer estoqueDisponivel,

        @Schema(example = "SKU12345")
        @NotBlank(message = "SKU é obrigatório")
        String sku,

        @Schema(example = "Informática")
        String categoria)
{}
