package com.fredsonchaves.algafood.api.controller;

import com.fredsonchaves.algafood.domain.entity.Estado;
import com.fredsonchaves.algafood.domain.exception.EntidadeEmUsoException;
import com.fredsonchaves.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.fredsonchaves.algafood.domain.repository.EstadoRepository;
import com.fredsonchaves.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping()
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);
        if (estado.isPresent()) {
            return ResponseEntity.ok(estado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado) {
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> atualizar(@RequestBody Estado estado, @PathVariable Long id) {
        Optional<Estado> estadoAtual = estadoRepository.findById(id);
        if (estadoAtual.isEmpty()) return ResponseEntity.notFound().build();
        BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
        cadastroEstadoService.salvar(estadoAtual.get());
        return ResponseEntity.ok().body(estadoAtual.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Estado> remover(@PathVariable Long id) {
        try {
            cadastroEstadoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException exception) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
