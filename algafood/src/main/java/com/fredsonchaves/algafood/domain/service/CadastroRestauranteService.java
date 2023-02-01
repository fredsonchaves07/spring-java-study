package com.fredsonchaves.algafood.domain.service;

import com.fredsonchaves.algafood.domain.entity.Cozinha;
import com.fredsonchaves.algafood.domain.entity.FormaPagamento;
import com.fredsonchaves.algafood.domain.entity.Restaurante;
import com.fredsonchaves.algafood.domain.exception.EntidadeEmUsoException;
import com.fredsonchaves.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.fredsonchaves.algafood.domain.repository.CozinhaRepository;
import com.fredsonchaves.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;


    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public void excluir(Long id) {
        try {
            restauranteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntidadeNaoEncontradaException(String.format("Restaurante de código %d não pode ser encontrado", id));
        } catch (DataIntegrityViolationException exception) {
            throw new EntidadeEmUsoException(String.format("Restaurante de código %d não pode ser removido", id));
        }
    }

    public Restaurante buscarOuFalhar(Long cidadeId) {
        return restauranteRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Restaurante de código %d não pode ser encontrado", cidadeId)));
    }

    @Transactional
    public void removerFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        for (FormaPagamento formaPagamento : restaurante.getFormaPagamentos()) {
            if (Objects.equals(formaPagamento.getId(), formaPagamentoId)) {
                restaurante.removerFormaPagamento(formaPagamento);
            }
        }
    }

    @Transactional
    public void adicionarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        for (FormaPagamento formaPagamento : restaurante.getFormaPagamentos()) {
            if (Objects.equals(formaPagamento.getId(), formaPagamentoId)) {
                restaurante.adicionarFormaPagamento(formaPagamento);
            }
        }
    }

    @Transactional
    public void ativar(Long restaurantId) {
        Restaurante restaurante = buscarOuFalhar(restaurantId);
        //falta implementar ativacao do restaurante
    }

    @Transactional
    public void ativar(List<Long> restaurantesIds) {
        restaurantesIds.forEach(this::ativar);
    }
}
