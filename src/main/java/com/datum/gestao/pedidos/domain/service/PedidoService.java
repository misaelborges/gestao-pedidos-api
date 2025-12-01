package com.datum.gestao.pedidos.domain.service;

import com.datum.gestao.pedidos.api.dto.cliente.ClienteResponseDTO;
import com.datum.gestao.pedidos.api.dto.itemPedido.ItemValidadoDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResponseDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import com.datum.gestao.pedidos.core.mapper.ClienteMapper;
import com.datum.gestao.pedidos.core.mapper.PedidoMapper;
import com.datum.gestao.pedidos.core.mapper.ProdutoMapper;
import com.datum.gestao.pedidos.domain.exception.ProdutoEmFaltaNoEstoqueException;
import com.datum.gestao.pedidos.domain.exception.QuantidadeProdutoInvalidoException;
import com.datum.gestao.pedidos.domain.model.ItemPedido;
import com.datum.gestao.pedidos.domain.model.Pedido;
import com.datum.gestao.pedidos.domain.model.StatusPedido;
import com.datum.gestao.pedidos.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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
                        throw new QuantidadeProdutoInvalidoException();
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


    private String geradorNumeroPedido() {
        String data = LocalDate.now().toString();
        String dataSemPonto = data.replace("-", "");
        String uuid = UUID.randomUUID().toString().substring(0,8).toUpperCase();
        return "PED-".concat(dataSemPonto + uuid);
    }
}
