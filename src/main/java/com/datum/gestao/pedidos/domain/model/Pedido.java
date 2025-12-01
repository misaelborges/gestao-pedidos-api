package com.datum.gestao.pedidos.domain.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "numero_pedido")
    private String numeroPedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @Column(nullable = false, name = "valor_total")
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(nullable = false, name = "data_pedido")
    private LocalDateTime dataPedido;

    @UpdateTimestamp
    @Column(nullable = false, name = "data_atualizacao")
    private LocalDateTime dataAtualizaçao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Long id, String numeroPedido, Cliente cliente, StatusPedido statusPedido, BigDecimal valorTotal,
                  LocalDateTime dataPedido, LocalDateTime dataAtualizaçao, String observacoes, List<ItemPedido> itens) {
        this.id = id;
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.statusPedido = statusPedido;
        this.valorTotal = valorTotal;
        this.dataPedido = dataPedido;
        this.dataAtualizaçao = dataAtualizaçao;
        this.observacoes = observacoes;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDateTime getDataAtualizaçao() {
        return dataAtualizaçao;
    }

    public void setDataAtualizaçao(LocalDateTime dataAtualizaçao) {
        this.dataAtualizaçao = dataAtualizaçao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
