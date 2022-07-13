package com.fredsonchaves.algafood.domain.service;

import com.fredsonchaves.algafood.domain.entity.Cidade;
import com.fredsonchaves.algafood.domain.entity.Estado;
import com.fredsonchaves.algafood.domain.exception.EntidadeEmUsoException;
import com.fredsonchaves.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.fredsonchaves.algafood.domain.repository.CidadeRepository;
import com.fredsonchaves.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        Long estadoid = cidade.getEstado().getId();
        Estado estado = estadoRepository.buscarPorId(estadoid);
        if (estado == null) {
            throw new EntidadeNaoEncontradaException(String.format("Estado de código %d não existe", estadoid));
        }
        cidade.setEstado(estado);
        return cidadeRepository.adicionar(cidade);
    }

    public void excluir(Long id) {
        try {

            cidadeRepository.remover(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntidadeNaoEncontradaException(String.format("Cidade de código %d não pode ser encontrado", id));
        } catch (DataIntegrityViolationException exception) {
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removido", id));
        }
    }
}
