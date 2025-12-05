package com.datum.gestao.pedidos.api.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

@Schema(name = "ClienteRequestDTO", description = "DTO para cadastro de cliente")
public record ClienteRequestDTO(

        @Schema(example = "Misael Borges Cancelier", description = "Nome completo do cliente")
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,

        @Schema(example = "misael.borges@email.com", description = "E-mail válido do cliente")
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @Schema(example = "85023101002", description = "CPF com 11 dígitos")
        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF deve conter exatamente 11 dígitos numéricos")
        String cpf,

        @Schema(example = "11987654321", description = "Telefone com DDD")
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(
                regexp = "\\d{10,11}",
                message = "Telefone deve conter 10 ou 11 dígitos (DDD + número)"
        )
        String telefone,

        @Schema(example = "01001000", description = "CEP do endereço")
        @NotBlank(message = "CEP é obrigatório")
        @Pattern(
                regexp = "\\d{8}",
                message = "CEP deve conter exatamente 8 dígitos"
        )
        String cep,

        @Schema(example = "Rua das Flores", description = "Logradouro")
        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,

        @Schema(example = "1000", description = "Número do endereço")
        @NotBlank(message = "Número é obrigatório")
        String numero,

        @Schema(example = "Apto 202", description = "Complemento (opcional)")
        String complemento,

        @Schema(example = "Centro", description = "Bairro")
        @NotBlank(message = "Bairro é obrigatório")
        String bairro,

        @Schema(example = "São Paulo", description = "Cidade")
        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        @Schema(example = "SP", description = "Estado (UF)")
        @NotBlank(message = "Estado é obrigatório")
        @Size(min = 2, max = 2, message = "Estado deve conter exatamente 2 letras (UF)")
        String estado

) {}
