package com.datum.gestao.pedidos.domain.service;

import com.datum.gestao.pedidos.api.dto.cliente.ClienteResponseDTO;
import com.datum.gestao.pedidos.api.dto.filtro.PedidoFiltro;
import com.datum.gestao.pedidos.api.dto.itemPedido.ItemValidadoDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResponseDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResumoResponseDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import com.datum.gestao.pedidos.core.mapper.ClienteMapper;
import com.datum.gestao.pedidos.core.mapper.PedidoMapper;
import com.datum.gestao.pedidos.core.mapper.ProdutoMapper;
import com.datum.gestao.pedidos.domain.exception.*;
import com.datum.gestao.pedidos.domain.model.ItemPedido;
import com.datum.gestao.pedidos.domain.model.Pedido;
import com.datum.gestao.pedidos.domain.model.StatusPedido;
import com.datum.gestao.pedidos.domain.repository.PedidoRepository;
import com.datum.gestao.pedidos.domain.specification.PedidoEspecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteMapper clienteMapper;
    private final PedidoMapper pedidoMapper;
    private final ProdutoMapper produtoMapper;
    private final ProdutoService produtoService;
    private final ClienteService clienteService;

    public PedidoService(PedidoRepository pedidoRepository, ClienteMapper clienteMapper, PedidoMapper pedidoMapper,
                         ProdutoMapper produtoMapper, ProdutoService produtoService, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteMapper = clienteMapper;
        this.pedidoMapper = pedidoMapper;
        this.produtoMapper = produtoMapper;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) {

        // 1. Validar cliente
        ClienteResponseDTO clienteResponseDTO = clienteService.buscarClientePorId(pedidoRequestDTO.clienteId());

        // 2. Validar produtos e estoque (em um único loop)
        //    - Para cada item:
        //      - Buscar produto
        //      - Verificar se existe
        //      - Verificar se quantidade > 0
        //      - Verificar se tem estoque >= quantidade
        //      - Calcular subtotal
        List<ItemValidadoDTO> itemValidados = pedidoRequestDTO.itens()
                .stream()
                .map(item -> {
                    ProdutoResponseDTO produtoResponseDTO = produtoService.buscarProdutoPorId(item.produtoId());
                    if (item.quantidade() <= 0) {
                        throw new QuantidadeProdutoInvalidaException();
                    }

                    if (produtoResponseDTO.estoqueDisponivel() < item.quantidade()) {
                        throw new ProdutoEmFaltaNoEstoqueException(item.produtoId());
                    }

                    BigDecimal subtotal = produtoResponseDTO.preco().multiply(BigDecimal.valueOf(item.quantidade()));
                    return new ItemValidadoDTO(produtoResponseDTO, item.quantidade(), subtotal);
                })
                .toList();

        // 3. Calcular valor total
        BigDecimal valorTotal = itemValidados
                .stream()
                .map(ItemValidadoDTO::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        // 4. Criar entidade Pedido
        Pedido pedido = new Pedido();
        pedido.setNumeroPedido(geradorNumeroPedido());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setCliente(clienteMapper.toCliente(clienteResponseDTO));
        pedido.setValorTotal(valorTotal);

        // 5. Criar entidades ItemPedido
        List<ItemPedido> itensPedidos = itemValidados
                .stream()
                .map(item -> {
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produtoMapper.toProduto(item.produto()));
                    itemPedido.setQuantidade(item.quantidade());
                    itemPedido.setPrecoUnitario(item.produto().preco());
                    itemPedido.setSubtotal(item.subtotal());
                    return itemPedido;
                })
                .toList();

        pedido.setItens(itensPedidos);

        // 6. Atualizar estoque dos produtos
        itemValidados.forEach(item -> {
            produtoService.baixarEstoque(item.produto().id(), item.quantidade());
        });

        // 7. Salvar pedido (cascade vai salvar os itens)
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // 8. Retornar DTO
        return pedidoMapper.toPedidoResponseDTO(pedidoSalvo);
    }

    public PedidoResponseDTO buscarPedidoIdNumeroPedido(Long pedidoId, String numeroPedido) {
        Specification<Pedido> spec = Specification.allOf(
                PedidoEspecification.comId(pedidoId),
                PedidoEspecification.comNumeroPedido(numeroPedido)
        );

        Pedido pedido = pedidoRepository.findOne(spec)
                .orElseThrow(() -> new PedidoNaoEncontradoException(
                        "Produto não encontrado" +
                                (pedidoId != null ? " com ID: " + pedidoId : "") +
                                (numeroPedido != null ? " com SKU: " + numeroPedido : "")
                ));

        return pedidoMapper.toPedidoResponseDTO(pedido);
    }

    public PedidoResponseDTO buscarPedidoId(Long pedidoId) {

        Pedido pedido = buscarPedido(pedidoId);

        return pedidoMapper.toPedidoResponseDTO(pedido);
    }

    public Page<PedidoResumoResponseDTO> buscarComFiltros(PedidoFiltro filtro, Pageable pageable) {
        Specification<Pedido> spec = Specification.allOf(
                PedidoEspecification.comStatus(filtro.status()),
                PedidoEspecification.comCliente(filtro.cliente()),
                PedidoEspecification.comData(filtro.data())
        );

        Page<Pedido> pedidoPage = pedidoRepository.findAll(spec, pageable);

        List<PedidoResumoResponseDTO> dtos = pedidoMapper.toPedidoResumoDTO(pedidoPage.getContent());
        return new PageImpl<>(dtos, pageable, pedidoPage.getTotalElements());
    }

    @Transactional
    public PedidoResponseDTO atualizarPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        if (pedido.getStatusPedido().equals(StatusPedido.AGUARDANDO_PAGAMENTO)) {
            pedido.setStatusPedido(StatusPedido.PAGAMENTO_CONFIRMADO);
        } else if(pedido.getStatusPedido().equals(StatusPedido.PAGAMENTO_CONFIRMADO)) {
            pedido.setStatusPedido(StatusPedido.EM_SEPARACAO);
        } else if(pedido.getStatusPedido().equals(StatusPedido.EM_SEPARACAO)) {
            pedido.setStatusPedido(StatusPedido.EM_TRANSPORTE);
        } else if(pedido.getStatusPedido().equals(StatusPedido.ENTREGUE)) {
            pedido.setStatusPedido(StatusPedido.EM_TRANSPORTE);
        }

        return pedidoMapper.toPedidoResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO atualizarPedido(Long id, String status) {
        Pedido pedido = buscarPedido(id);
        StatusPedido statusEnum;
        try {
            statusEnum = StatusPedido.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusInvalidoException(status);
        }
        pedido.setStatusPedido(StatusPedido.valueOf(status));

        return pedidoMapper.toPedidoResponseDTO(pedido);
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.setStatusPedido(StatusPedido.CANCELADO);
    }

    private Pedido buscarPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(
                () -> new PedidoNaoEncontradoException(pedidoId));
    }


    private String geradorNumeroPedido() {
        String data = LocalDate.now().toString();
        String dataSemPonto = data.replace("-", "");
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "PED-".concat(dataSemPonto + uuid);
    }
}
