package com.datum.gestao.pedidos.api.dto.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Schema(name = "ProdutoAtualizaRequestDTO", description = "Atualização de produto")
public record ProdutoAtualizaRequestDTO(

        @Schema(example = "Notebook Gamer")
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Schema(example = "RTX 4060")
        String descricao,

        @Schema(example = "5500.00")
        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        BigDecimal preco,

        @Schema(example = "15")
        @NotNull(message = "Estoque é obrigatório")
        @PositiveOrZero(message = "Estoque não pode ser negativo")
        Integer estoqueDisponivel,

        @Schema(example = "Periféricos")
        String categoria
) {}
