package com.datum.gestao.pedidos.api.dto.cliente;

public record ClienteResumoResponseDTO(Long id, String nome, String email, String telefone, String estado, String cidade ) {
}