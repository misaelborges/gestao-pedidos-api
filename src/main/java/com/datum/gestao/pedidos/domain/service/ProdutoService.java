package com.datum.gestao.pedidos.domain.service;

import com.datum.gestao.pedidos.api.dto.produto.ProdutoAtualizaRequestDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoRequestDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResponseDTO;
import com.datum.gestao.pedidos.api.dto.produto.ProdutoResumoResponseDTO;
import com.datum.gestao.pedidos.core.mapper.ProdutoMapper;
import com.datum.gestao.pedidos.domain.exception.ProdutoNaoEncontradoException;
import com.datum.gestao.pedidos.domain.model.Produto;
import com.datum.gestao.pedidos.domain.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public Page<ProdutoResumoResponseDTO> listarProdutos(Pageable pageable) {
        Page<Produto> produtoPage = produtoRepository.findAllProdutoByAtivoTrue(pageable);
        List<ProdutoResumoResponseDTO> dtos = produtoMapper.toProdutoResumoDTO(produtoPage.getContent());
        return new PageImpl<>(dtos, pageable, produtoPage.getTotalElements());
    }

    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Produto produto = buscarProduto(id);
        return produtoMapper.toProdutoResponseDTO(produto);
    }

    public ProdutoResponseDTO salvarProduto(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoMapper.toProduto(produtoRequestDTO);
        produto.setAtivo(true);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.toProdutoResponseDTO(produtoSalvo);
    }

    @Transactional
    public ProdutoResponseDTO atualizarProdutoPorId(Long id, ProdutoAtualizaRequestDTO produtoAtualizaRequestDTO) {
        Produto produtoBanco = buscarProduto(id);

        atualizarProduto(produtoAtualizaRequestDTO, produtoBanco);

        return produtoMapper.toProdutoResponseDTO(produtoBanco);
    }

    @Transactional
    public void deletarProtudoPorId(Long id) {
        Produto produto = buscarProduto(id);
        produto.setAtivo(false);
    }

    private void atualizarProduto(ProdutoAtualizaRequestDTO produtoAtualizaRequestDTO, Produto produto) {
        produto.setNome(produtoAtualizaRequestDTO.nome());
        produto.setDescricao(produtoAtualizaRequestDTO.descricao());
        produto.setPreco(produtoAtualizaRequestDTO.preco());
        produto.setEstoqueDisponivel(produtoAtualizaRequestDTO.estoqueDisponivel());
        produto.setCategoria(produtoAtualizaRequestDTO.categoria());
    }

    private Produto buscarProduto(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }
}
