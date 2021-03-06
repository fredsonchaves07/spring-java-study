package com.fredsonchaves.algafood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredsonchaves.algafood.domain.entity.Restaurante;
import com.fredsonchaves.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.fredsonchaves.algafood.domain.repository.RestauranteRepository;
import com.fredsonchaves.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping()
    public List<Restaurante> listas() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Restaurante> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);
        } catch (EntidadeNaoEncontradaException exception) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(@RequestBody Restaurante restaurante, @PathVariable Long id) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);
        if (restauranteAtual.isEmpty()) return ResponseEntity.notFound().build();
        BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
        cadastroRestauranteService.salvar(restauranteAtual.get());
        return ResponseEntity.ok().body(restauranteAtual.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
        if (restauranteAtual.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        merge(campos, restauranteAtual.get());
        return atualizar(restauranteAtual.get(), restauranteId);
    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
        camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }
}
