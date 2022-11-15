package com.fredsonchaves.algafood.domain.service;

import com.fredsonchaves.algafood.domain.entity.FotoProduto;
import com.fredsonchaves.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto) {
        return produtoRepository.save(fotoProduto);
    }
}
