package com.datum.gestao.pedidos.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ClienteNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de um cliente com o id: %d", id));
    }
}
