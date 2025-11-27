package com.datum.gestao.pedidos.domain.service;

import com.datum.gestao.pedidos.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public Page<ProdutoResumoResponseDTO> listarProdutos(Pageable pageable) {
        Page<Produto> produtoPage = produtoRepository.findAllProdutoByAtivoTrue(pageable);
        List<ProdutoResumoResponseDTO> dtos = produtoMapper.toProdutoResumoDTO(produtoPage.getContent());
        return new PageImpl<>(dtos, pageable, produtoPage.getTotalElements());
    }
}
