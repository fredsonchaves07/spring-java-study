package com.fredsonchaves.algafood.api.controller;

import com.fredsonchaves.algafood.domain.entity.Cidade;
import com.fredsonchaves.algafood.domain.exception.EntidadeEmUsoException;
import com.fredsonchaves.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.fredsonchaves.algafood.domain.repository.CidadeRepository;
import com.fredsonchaves.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping()
    public List<Cidade> listas() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        if (cidade.isPresent()) {
            return ResponseEntity.ok(cidade.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody Cidade cidade) {
        return cadastroCidadeService.salvar(cidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizar(@RequestBody Cidade cidade, @PathVariable Long id) {
        Optional<Cidade> cidadeAtual = cidadeRepository.findById(id);
        if (cidadeAtual.isEmpty()) return ResponseEntity.notFound().build();
        BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");
        cadastroCidadeService.salvar(cidadeAtual.get());
        return ResponseEntity.ok().body(cidadeAtual.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> remover(@PathVariable Long id) {
        try {
            cadastroCidadeService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException exception) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
