package com.datum.gestao.pedidos.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class QuantidadeProdutoInvalidaException extends NegocioException {

    public QuantidadeProdutoInvalidaException(String mensagem) {
        super(mensagem);
    }

    public QuantidadeProdutoInvalidaException() {
        this("Valor para um pedido deve ser positívo");
    }

}
