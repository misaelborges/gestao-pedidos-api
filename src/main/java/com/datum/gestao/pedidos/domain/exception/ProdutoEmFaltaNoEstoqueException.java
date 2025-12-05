package com.datum.gestao.pedidos.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ProdutoEmFaltaNoEstoqueException extends NegocioException {

    public ProdutoEmFaltaNoEstoqueException(String mensagem) {
        super(mensagem);
    }

    public ProdutoEmFaltaNoEstoqueException(Long id) {
        this(String.format("Produto com o id: %d está em falta no momemento", id));
    }

}
