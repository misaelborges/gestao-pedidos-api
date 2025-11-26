package com.datum.gestao.pedidos.api.dto.cliente;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF deve conter exatamente 11 dígitos numéricos")
        String cpf,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(
                regexp = "\\d{10,11}",
                message = "Telefone deve conter 10 ou 11 dígitos (DDD + número)"
        )
        String telefone,

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(
                regexp = "\\d{8}",
                message = "CEP deve conter exatamente 8 dígitos"
        )
        String cep,

        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,

        @NotBlank(message = "Número é obrigatório")
        String numero,

        String complemento,

        @NotBlank(message = "Bairro é obrigatório")
        String bairro,

        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        @NotBlank(message = "Estado é obrigatório")
        @Size(min = 2, max = 2, message = "Estado deve conter exatamente 2 letras (UF)")
        String estado

) {}
