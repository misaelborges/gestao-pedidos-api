package com.datum.gestao.pedidos.domain.service;

import com.datum.gestao.pedidos.api.dto.cliente.ClienteResponseDTO;
import com.datum.gestao.pedidos.api.dto.filtro.PedidoFiltro;
import com.datum.gestao.pedidos.api.dto.itemPedido.ItemPedidoRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoRequestDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResponseDTO;
import com.datum.gestao.pedidos.api.dto.pedido.PedidoResumoResponseDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import com.datum.gestao.pedidos.core.mapper.ClienteMapper;
import com.datum.gestao.pedidos.core.mapper.PedidoMapper;
import com.datum.gestao.pedidos.core.mapper.ProdutoMapper;
import com.datum.gestao.pedidos.domain.exception.PedidoNaoEncontradoException;
import com.datum.gestao.pedidos.domain.model.Cliente;
import com.datum.gestao.pedidos.domain.model.Pedido;
import com.datum.gestao.pedidos.domain.model.Produto;
import com.datum.gestao.pedidos.domain.model.StatusPedido;
import com.datum.gestao.pedidos.domain.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private ProdutoMapper produtoMapper;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private Pedido pedidoMultiplosItens;
    private PedidoRequestDTO pedidoRequestDTO;
    private PedidoRequestDTO pedidoRequestDTOMultiplosItens;
    private PedidoResponseDTO pedidoResponseDTO;
    private PedidoResumoResponseDTO pedidoResumoDTO;
    private ClienteResponseDTO clienteResponseDTO;
    private ProdutoResponseDTO produtoResponseDTO;
    private ProdutoResponseDTO produtoResponseDTO2;
    private Cliente cliente;
    private Produto produto;
    private Produto produto2;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");

        clienteResponseDTO = new ClienteResponseDTO(1L, "João Silva", "joao@email.com", "12345678901",
                "11987654321", "01001000", "Rua A", "123", "Apto 2",
                "Centro", "São Paulo", "SP", LocalDateTime.now());

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook Dell");
        produto.setPreco(new BigDecimal("3000.00"));
        produto.setEstoqueDisponivel(100);

        produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Mouse Logitech");
        produto2.setPreco(new BigDecimal("50.00"));
        produto2.setEstoqueDisponivel(100);

        produtoResponseDTO = new ProdutoResponseDTO(1L, "Notebook Dell", "Notebook i7",
                new BigDecimal("3000.00"), 100, "SKU12345", "Informática",
                LocalDateTime.now(), LocalDateTime.now());

        produtoResponseDTO2 = new ProdutoResponseDTO(2L, "Mouse Logitech", "Mouse logitech",
                new BigDecimal("50.00"), 100, "SKU54321", "Periféricos",
                LocalDateTime.now(), LocalDateTime.now());

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setNumeroPedido("PED-2025-0001");
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setValorTotal(new BigDecimal("15000.00"));
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setObservacoes("Entregar no período da manhã");

        pedidoMultiplosItens = new Pedido();
        pedidoMultiplosItens.setId(2L);
        pedidoMultiplosItens.setNumeroPedido("PED-2025-0002");
        pedidoMultiplosItens.setCliente(cliente);
        pedidoMultiplosItens.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedidoMultiplosItens.setValorTotal(new BigDecimal("6150.00")); // (3000*2) + (50*3)
        pedidoMultiplosItens.setDataPedido(LocalDateTime.now());

        pedidoResponseDTO = new PedidoResponseDTO(1L, "PED-2025-0001", "João Silva",
                StatusPedido.AGUARDANDO_PAGAMENTO, new BigDecimal("15000.00"), LocalDateTime.now(), LocalDateTime.now()
                ,"Entregar no período da manhã");

        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO(1L, 5, "Produtos separados");
        pedidoRequestDTO = new PedidoRequestDTO(1L, List.of(itemDTO), "Entregar no período da manhã");

        ItemPedidoRequestDTO item1 = new ItemPedidoRequestDTO(1L, 2, null);
        ItemPedidoRequestDTO item2 = new ItemPedidoRequestDTO(2L, 3, null);
        pedidoRequestDTOMultiplosItens = new PedidoRequestDTO(1L, List.of(item1, item2), null);

        pedidoResumoDTO = new PedidoResumoResponseDTO(1L, "PED-2025-0001",
                cliente.getNome(), StatusPedido.AGUARDANDO_PAGAMENTO, LocalDateTime.now(), null);
    }

    @DisplayName("Dado um pedido válido, criarPedido deve retornar um PedidoResponseDTO")
    void deveCriarPedidoComSucesso() {
        // Arrange
        when(clienteService.buscarClientePorId(1L))
                .thenReturn(clienteResponseDTO);

        when(produtoService.buscarProdutoPorId(1L))
                .thenReturn(produtoResponseDTO);

        when(clienteMapper.toCliente(clienteResponseDTO))
                .thenReturn(cliente);

        when(produtoMapper.toProduto(produtoResponseDTO))
                .thenReturn(produto);

        when(pedidoRepository.save(any(Pedido.class)))
                .thenReturn(pedido);

        when(pedidoMapper.toPedidoResponseDTO(pedido))
                .thenReturn(pedidoResponseDTO);

        // Act
        PedidoResponseDTO resultado = pedidoService.criarPedido(pedidoRequestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(pedidoResponseDTO.numeroPedido(), resultado.numeroPedido());
        assertEquals(pedidoResponseDTO.statusPedido(), resultado.statusPedido());
        assertEquals(new BigDecimal("15000.00"), resultado.valorTotal());
        assertEquals("Entregar no período da manhã", resultado.observacoes());

        verify(clienteService, times(1)).buscarClientePorId(1L);
        verify(produtoService, times(1)).buscarProdutoPorId(1L);
        verify(produtoService, times(1)).baixarEstoque(1L, 5);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(pedidoMapper, times(1)).toPedidoResponseDTO(pedido);
    }


    @Test
    @DisplayName("Dado um id ou número válido, buscarPedidoIdNumeroPedido deve retornar um pedido")
    void deveBuscarPedidoIdNumeroPedido() {
        when(pedidoRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.of(pedido));

        when(pedidoMapper.toPedidoResponseDTO(pedido))
                .thenReturn(pedidoResponseDTO);

        PedidoResponseDTO resultado = pedidoService.buscarPedidoIdNumeroPedido(1L, "PED-2025-0001");

        assertEquals(pedidoResponseDTO.numeroPedido(), resultado.numeroPedido());

        verify(pedidoRepository, times(1)).findOne(any(Specification.class));
        verify(pedidoMapper, times(1)).toPedidoResponseDTO(pedido);
    }

    @Test
    @DisplayName("Dado um id ou número inválido, buscarPedidoIdNumeroPedido deve lançar PedidoNaoEncontradoException")
    public void deveFalhaBuscarPedidoIdNumeroPedido() {
        when(pedidoRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.empty());

        assertThrows(PedidoNaoEncontradoException.class, () -> {
            pedidoService.buscarPedidoIdNumeroPedido(1L, "PED-2025-0001");
        });

        verify(pedidoRepository, times(1)).findOne(any(Specification.class));
        verifyNoMoreInteractions(pedidoRepository);
    }

    @Test
    @DisplayName("Dado um id inválido, buscarPedidoId deve lançar PedidoNaoEncontradoException")
    void deveBuscarPedidoId() {
        when(pedidoRepository.findById(1L))
                .thenReturn(Optional.of(pedido));

        when(pedidoMapper.toPedidoResponseDTO(pedido))
                .thenReturn(pedidoResponseDTO);

        PedidoResponseDTO resultado = pedidoService.buscarPedidoId(1L);

        assertNotNull(resultado);
        assertEquals(pedidoResponseDTO.numeroPedido(), resultado.numeroPedido());

        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoMapper, times(1)).toPedidoResponseDTO(pedido);
    }

    @Test
    @DisplayName("Dado filtros válidos, buscarComFiltros deve retornar pedidos filtrados")
    void deveBuscarComFiltros() {
        Pageable pageable = PageRequest.of(0, 10);
        PedidoFiltro filtro = new PedidoFiltro(
                StatusPedido.AGUARDANDO_PAGAMENTO.getDescricao(),
                cliente.getNome(),
                LocalDateTime.now());

        List<Pedido> pedidos = List.of(pedido);

        Page<Pedido> pagePedidos = new PageImpl<>(pedidos, pageable, pedidos.size());

        List<PedidoResumoResponseDTO> dtos = List.of(pedidoResumoDTO);

        when(pedidoRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(pagePedidos);

        when(pedidoMapper.toPedidoResumoDTO(pedidos))
                .thenReturn(dtos);

        Page<PedidoResumoResponseDTO> resultado = pedidoService.buscarComFiltros(filtro, pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        assertEquals(pedidoResumoDTO.numeroPedido(), resultado.getContent().get(0).numeroPedido());

        verify(pedidoRepository, times(1))
                .findAll(any(Specification.class), eq(pageable));

        verify(pedidoMapper, times(1))
                .toPedidoResumoDTO(pedidos);
    }

    @Test
    @DisplayName("Dado um pedido válido, atualizarPedido deve atualizar um pedido já existente")
    void deveAtualizarPedido() {
        when(pedidoRepository.findById(1L))
                .thenReturn(Optional.of(pedido));

        when(pedidoMapper.toPedidoResponseDTO(pedido))
                .thenReturn(pedidoResponseDTO);

        PedidoResponseDTO resultado = pedidoService.atualizarPedido(1L);

        assertEquals(pedidoResponseDTO.statusPedido(), resultado.statusPedido());

        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoMapper, times(1)).toPedidoResponseDTO(pedido);
    }

    @Test
    @DisplayName("Dado um pedido válido, atualizarPedido deve atualizar um pedido já existente")
    void deveAtualizarPedidoComStatus() {
        when(pedidoRepository.findById(1L))
                .thenReturn(Optional.of(pedido));

        when(pedidoMapper.toPedidoResponseDTO(pedido))
                .thenReturn(pedidoResponseDTO);

        PedidoResponseDTO resultado =
                pedidoService.atualizarPedido(1L, StatusPedido.PAGAMENTO_CONFIRMADO.name());

        assertEquals(StatusPedido.PAGAMENTO_CONFIRMADO, pedido.getStatusPedido());
        assertEquals(pedidoResponseDTO.statusPedido(), resultado.statusPedido());

        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoMapper, times(1)).toPedidoResponseDTO(pedido);
    }

    @Test
    @DisplayName("Dado um id válido, cancelarPedido deve cancelar um pedido")
    void cancelarPedido() {
        when(pedidoRepository.findById(1L))
                .thenReturn(Optional.of(pedido));

        pedidoService.cancelarPedido(1L);

        assertEquals(StatusPedido.CANCELADO, pedido.getStatusPedido());

        verify(pedidoRepository, times(1)).findById(1L);
    }
}