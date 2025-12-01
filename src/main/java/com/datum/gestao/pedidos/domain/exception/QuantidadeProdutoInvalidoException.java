package com.datum.gestao.pedidos.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class QuantidadeProdutoInvalidoException extends NegocioException {

    public QuantidadeProdutoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public QuantidadeProdutoInvalidoException() {
        this("Valor para um pedido deve ser positívo");
    }

}
