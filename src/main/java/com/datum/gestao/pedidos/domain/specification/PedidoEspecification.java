package com.datum.gestao.pedidos.domain.specification;

import com.datum.gestao.pedidos.domain.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

public class PedidoEspecification {

    public static Specification<Pedido> comId(Long id) {
        return (root, query, cb) ->
                id == null ? null : cb.equal(root.get("id"), id);
    }

    public static Specification<Pedido> comNumeroPedido(String numeroPedido) {
        return (root, query, cb) ->
                numeroPedido == null ? null :
                        cb.equal(
                                cb.lower(root.get("numeroPedido")),
                                numeroPedido.toLowerCase()
                        );
    }

    public static Specification<Pedido> ativoTrue() {
        return (root, query, cb) -> cb.isTrue(root.get("ativo"));
    }
}
