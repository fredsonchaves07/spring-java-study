package com.fredsonchaves.algafood.domain.service;

import com.fredsonchaves.algafood.domain.entity.Estado;
import com.fredsonchaves.algafood.domain.exception.EntidadeEmUsoException;
import com.fredsonchaves.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.fredsonchaves.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return estadoRepository.adicionar(estado);
    }

    public void excluir(Long id) {
        try {

            estadoRepository.remover(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntidadeNaoEncontradaException(String.format("Estado de código %d não pode ser encontrado", id));
        } catch (DataIntegrityViolationException exception) {
            throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removido", id));
        }
    }
}
