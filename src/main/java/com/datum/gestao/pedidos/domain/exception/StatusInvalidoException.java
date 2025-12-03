package com.datum.gestao.pedidos.domain.exception;

import com.datum.gestao.pedidos.domain.model.StatusPedido;

public class StatusInvalidoException extends NegocioException {

    public StatusInvalidoException(String mensagem) {
        super(mensagem);
    }

    public StatusInvalidoException(StatusPedido status) {
        this(String.format("Status inválido: %s", status));
    }
}
