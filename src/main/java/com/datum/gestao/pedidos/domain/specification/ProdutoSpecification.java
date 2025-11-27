package com.datum.gestao.pedidos.domain.specification;

import com.datum.gestao.pedidos.domain.model.Produto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProdutoSpecification {

    public static Specification<Produto> comId(Long id) {
        return (root, query, cb) ->
                id == null ? null : cb.equal(root.get("id"), id);
    }

    public static Specification<Produto> comSku(String sku) {
        return (root, query, cb) ->
                sku == null ? null :
                        cb.equal(
                                cb.lower(root.get("sku")),
                                sku.toLowerCase()
                        );
    }

    public static Specification<Produto> comCategoria(String categoria) {
        return (root, query, cb) ->
                categoria == null ? null :
                        cb.equal(
                                cb.lower(root.get("categoria")),
                                categoria.toLowerCase()
                        );
    }

    public static Specification<Produto> nomeContem(String nome) {
        return (root, query, cb) ->
                nome == null ? null :
                        cb.like(
                                cb.lower(root.get("nome")),
                                "%" + nome.toLowerCase() + "%"
                        );
    }

    public static Specification<Produto> precoEntre(BigDecimal precoMin, BigDecimal precoMax) {
        return (root, query, cb) -> {

            if (precoMin == null && precoMax == null) {
                return cb.conjunction();
            }

            if (precoMin != null && precoMax != null) {
                return cb.between(root.get("preco"), precoMin, precoMax);
            }

            if (precoMin != null) {
                return cb.greaterThanOrEqualTo(root.get("preco"), precoMin);
            }

            return cb.lessThanOrEqualTo(root.get("preco"), precoMax);
        };
    }

    public static Specification<Produto> ativoTrue() {
        return (root, query, cb) -> cb.isTrue(root.get("ativo"));
    }
}

