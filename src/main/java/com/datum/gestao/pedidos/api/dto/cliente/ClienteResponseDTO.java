package com.datum.gestao.pedidos.api.dto.cliente;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        LocalDateTime dataCadastro) {
}
