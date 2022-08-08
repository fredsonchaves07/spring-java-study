package com.fredsonchaves.algafood.api.controller;

import com.fredsonchaves.algafood.domain.entity.Cozinha;
import com.fredsonchaves.algafood.domain.exception.EntidadeEmUsoException;
import com.fredsonchaves.algafood.domain.repository.CozinhaRepository;
import com.fredsonchaves.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping()
    public List<Cozinha> listas() {
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
        if (cozinha.isPresent()) {
            return ResponseEntity.ok(cozinha.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha, @PathVariable Long id) {
        Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);
        if (cozinhaAtual.isEmpty()) return ResponseEntity.notFound().build();
        BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
        cadastroCozinhaService.salvar(cozinhaAtual.get());
        return ResponseEntity.ok().body(cozinhaAtual.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long id) {
        try {
            cadastroCozinhaService.excluir(id);
            return ResponseEntity.noContent().build();
//        } catch (EntidadeNaoEncontradaException exception) {
//            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
