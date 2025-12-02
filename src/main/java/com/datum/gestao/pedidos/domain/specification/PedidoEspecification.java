package com.datum.gestao.pedidos.domain.specification;

import com.datum.gestao.pedidos.domain.model.Pedido;
import com.datum.gestao.pedidos.domain.model.StatusPedido;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

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


    public static Specification<Pedido> comStatus(String status) {
        return (root, query, cb) ->
                status == null ? null :
                        cb.equal(
                                root.get("statusPedido"),
                                StatusPedido.valueOf(status.toUpperCase())
                        );
    }



    public static Specification<Pedido> comCliente(String cliente) {
        return (root, query, cb) ->
                cliente == null ? null :
                        cb.like(
                                cb.lower(root.get("cliente").get("nome")),
                                "%" + cliente.toLowerCase() + "%"
                        );
    }


    public static Specification<Pedido> comData(LocalDateTime data) {
        return (root, query, cb) ->
                data == null ? null :
                        cb.equal(root.get("dataPedido"), data);
    }
}
