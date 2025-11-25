package com.datum.gestao.pedidos.domain.model;

public enum StatusPedido {

    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    PAGAMENTO_CONFIRMADO("Pagamento Confirmado"),
    EM_SEPARACAO("Em Separação"),
    EM_TRANSPORTE("Em Transporte"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
