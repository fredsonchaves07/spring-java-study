package com.fredsonchaves.algafood.api.controller;

import com.fredsonchaves.algafood.domain.entity.Cozinha;
import com.fredsonchaves.algafood.domain.repository.CozinhaRepository;
import com.fredsonchaves.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping()
    public Page<Cozinha> listas(Pageable pageable) {
        Page<Cozinha> pageCozinha =  cozinhaRepository.findAll(pageable);
        return pageCozinha;
    }

    @GetMapping("/{id}")
    public Cozinha buscar(@PathVariable Long id) {
        return cadastroCozinhaService.buscarOuFalhar(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@RequestBody Cozinha cozinha, @PathVariable Long id) {
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(id);
        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        return cadastroCozinhaService.salvar(cozinhaAtual);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        cadastroCozinhaService.excluir(id);
    }
}
