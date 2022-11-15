package com.fredsonchaves.algafood.api.controller;

import com.fredsonchaves.algafood.domain.entity.FotoProduto;
import com.fredsonchaves.algafood.domain.entity.Produto;
import com.fredsonchaves.algafood.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestParam MultipartFile arquivo
    ) {
        var nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();
        var arquivoFoto = Path.of("/upload", nomeArquivo);
        try {
            arquivo.transferTo(arquivoFoto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MultipartFile arquivoFile = fotoProdutoInput.getArquivo();

        Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(arquivoFile.getContentType());
        fotoProduto.setTamanho(arquivoFile.getSize());
        fotoProduto.setNomeArquivo(arquivoFile.getOriginalFilename());
        catalogoFotoProdutoService.salvar(fotoProduto);
    }
}
