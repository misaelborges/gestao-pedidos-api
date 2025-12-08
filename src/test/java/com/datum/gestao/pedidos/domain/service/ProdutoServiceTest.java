package com.datum.gestao.pedidos.domain.service;

import com.datum.gestao.pedidos.api.dto.filtro.ProdutoFiltro;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoAtualizaRequestDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoRequestDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResumoResponseDTO;
import com.datum.gestao.pedidos.core.mapper.ProdutoMapper;
import com.datum.gestao.pedidos.domain.exception.ProdutoNaoEncontradoException;
import com.datum.gestao.pedidos.domain.model.Cliente;
import com.datum.gestao.pedidos.domain.model.Produto;
import com.datum.gestao.pedidos.domain.repository.ProdutoRepository;
import com.datum.gestao.pedidos.domain.specification.ProdutoSpecification;
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
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;
    private Produto produto2;
    private ProdutoRequestDTO produtoRequestDTO;
    private ProdutoResponseDTO produtoResponseDTO;
    private ProdutoResponseDTO produtoAtualizadoDTO;
    private ProdutoResumoResponseDTO produtoResumoResponseDTO;
    private ProdutoResumoResponseDTO produtoResumoResponseDTO2;

    @BeforeEach
    void setUp() {
        produto = new Produto(1L, "Notebook Gamer", "Notebook com GPU dedicada RTX", BigDecimal.valueOf(6500.00), 10,
                "NBK-GAMER-001", "Eletrônicos", true,  LocalDateTime.now(), null);

        produto2 = new Produto(2L, "Mouse Sem Fio", "Mouse ergonômico com 2400 DPI", BigDecimal.valueOf(89.90), 50,
                "MOUSE-SF-2400", "Acessórios", true, LocalDateTime.now(), null);

        produtoResponseDTO = new ProdutoResponseDTO(1L, "Notebook Gamer", "Notebook com GPU dedicada RTX", BigDecimal.valueOf(6500.00), 10,
                "NBK-GAMER-001", "Eletrônicos",  LocalDateTime.now(), null);

        produtoAtualizadoDTO = new ProdutoResponseDTO(1L, "Notebook Gamer", "Notebook com GPU dedicada RTX", BigDecimal.valueOf(6500.00), 9,
                "NBK-GAMER-001", "Eletrônicos",  LocalDateTime.now(), null);

        produtoResumoResponseDTO = new ProdutoResumoResponseDTO(1L, "Notebook Gamer", BigDecimal.valueOf(6500.00),
                10, "Eletrônicos");

        produtoResumoResponseDTO2 = new ProdutoResumoResponseDTO(2L, "Mouse Sem Fio", BigDecimal.valueOf(89.90),
                50, "Acessórios");

        produtoRequestDTO = new ProdutoRequestDTO("Notebook Gamer", "Notebook com GPU dedicada RTX",
                BigDecimal.valueOf(6500.00), 10, "NBK-GAMER-001", "Eletrônicos");
    }

    @Test
    @DisplayName("Dado um id válido, buscarProdutoPorId deve retornar um produto")
    void deveBuscarProdutoPorId() {
        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        when(produtoMapper.toProdutoResponseDTO(produto))
                .thenReturn(produtoResponseDTO);

        ProdutoResponseDTO resultado = produtoService.buscarProdutoPorId(1L);

        assertNotNull(resultado);
        assertEquals(produtoResponseDTO.nome(), resultado.nome());
        assertEquals(produtoResponseDTO.sku(), resultado.sku());
        assertTrue(produto.getAtivo());

        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoMapper, times(1)).toProdutoResponseDTO(produto);
    }

    @Test
    @DisplayName("Dado um id inválido, buscarProdutoPorId deve retornar lançar ProdutoNaoEncontradoException")
    void deveFalharAoBuscarProdutoPorId() {
        when(produtoRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> {
            produtoService.buscarProdutoPorId(10L);
        });

        verify(produtoRepository, times(1)).findById(10L);
    }

    @Test
    @DisplayName("Dado um id ou sky válido, buscarProdutoSkuId deve retornar um produto")
    void deveBuscarProdutoSkuId() {

        when(produtoRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.of(produto));

        when(produtoMapper.toProdutoResponseDTO(produto))
                .thenReturn(produtoResponseDTO);

        ProdutoResponseDTO resultado = produtoService.buscarProdutoSkuId(1L, "NBK-GAMER-001");

        assertEquals(produtoResponseDTO.nome(), resultado.nome());
        assertEquals(produtoResponseDTO.sku(), resultado.sku());

        verify(produtoRepository, times(1)).findOne(any(Specification.class));
        verify(produtoMapper, times(1)).toProdutoResponseDTO(produto);
    }


    @Test
    @DisplayName("Dado um id ou sky inválido, buscarProdutoSkuId deve lançar um ProdutoNaoEncontradoException")
    void deveFalhaBuscarProdutoSkuId() {

        when(produtoRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> {
            produtoService.buscarProdutoSkuId(1L, "NBK-GAMER-001");
        });

        verify(produtoRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    @DisplayName("Dado filtros válidos, buscarComFiltros deve retornar produtos filtrados")
    void deveBuscarComFiltros() {

        Pageable pageable = PageRequest.of(0, 10);

        ProdutoFiltro filtro = new ProdutoFiltro(
                "Notebook",
                "Eletrônicos",
                BigDecimal.valueOf(5000),
                BigDecimal.valueOf(8000)
        );

        List<Produto> produtos = List.of(produto, produto2);

        Page<Produto> pageProdutos = new PageImpl<>(produtos, pageable, produtos.size());

        List<ProdutoResumoResponseDTO> dtos = List.of(produtoResumoResponseDTO, produtoResumoResponseDTO2);

        when(produtoRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(pageProdutos);

        when(produtoMapper.toProdutoResumoDTO(produtos))
                .thenReturn(dtos);

        Page<ProdutoResumoResponseDTO> resultado = produtoService.buscarComFiltros(filtro, pageable);

        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals(produtoResumoResponseDTO.nome(), resultado.getContent().get(0).nome());
        assertEquals(produtoResumoResponseDTO2.nome(), resultado.getContent().get(1).nome());

        verify(produtoRepository, times(1))
                .findAll(any(Specification.class), eq(pageable));

        verify(produtoMapper, times(1))
                .toProdutoResumoDTO(produtos);
    }

    @Test
    @DisplayName("Dado um produto válidos, salvarProduto deve salvar um novo produto")
    void deveSalvarProduto() {

        when(produtoMapper.toProduto(produtoRequestDTO))
                .thenReturn(produto);

        when(produtoRepository.save(produto))
                .thenReturn(produto);

        when(produtoMapper.toProdutoResponseDTO(produto))
                .thenReturn(produtoResponseDTO);

        ProdutoResponseDTO resultado = produtoService.salvarProduto(produtoRequestDTO);

        assertNotNull(resultado);
        assertTrue(produto.getAtivo());
        assertEquals(produtoResponseDTO.nome(), resultado.nome());

        verify(produtoRepository, times(1)).save(produto);
        verify(produtoMapper, times(1)).toProduto(produtoRequestDTO);
        verify(produtoMapper, times(1)).toProdutoResponseDTO(produto);
    }

    @Test
    @DisplayName("Dado um produto válido, atualizarProdutoPorId deve atualizar um produto já existente")
    void deveAtualizarProdutoPorId() {
        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        when(produtoMapper.toProdutoResponseDTO(produto))
                .thenReturn(produtoAtualizadoDTO);

        ProdutoResponseDTO resultado = produtoService.atualizarProdutoPorId(1L, new ProdutoAtualizaRequestDTO("Notebook Gamer",
                "Notebook com GPU dedicada RTX", BigDecimal.valueOf(6500.00), 9,
                "Eletrônicos"));

        assertNotNull(resultado);
        assertEquals(produtoResponseDTO.nome(), resultado.nome());
        assertEquals(produtoAtualizadoDTO.estoqueDisponivel(), resultado.estoqueDisponivel());

        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoMapper, times(1)).toProdutoResponseDTO(produto);
    }

    @Test
    void deletarProtudoPorId() {
        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        produtoService.deletarProtudoPorId(1L);

        assertFalse(produto.getAtivo());

        verify(produtoRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    void baixarEstoque() {
        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        produtoService.baixarEstoque(1L, 2);

        assertEquals(8, produto.getEstoqueDisponivel());

        verify(produtoRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(produtoRepository);
    }
}