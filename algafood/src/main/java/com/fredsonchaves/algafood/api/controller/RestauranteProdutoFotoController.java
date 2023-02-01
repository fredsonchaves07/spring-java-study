package com.fredsonchaves.algafood.api.controller;

import com.fredsonchaves.algafood.domain.entity.FotoProduto;
import com.fredsonchaves.algafood.domain.entity.Produto;
import com.fredsonchaves.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.fredsonchaves.algafood.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoStorageService fotoStorageService;

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

    @GetMapping
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                                          @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypeList = MediaType.parseMediaTypes(acceptHeader);
            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypeList);
            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaType, List<MediaType> mediaTypeList) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypeList.stream()
                .anyMatch(mediaType1 -> mediaType1.isCompatibleWith(mediaType));
        if (!compativel) throw new HttpMediaTypeNotAcceptableException(mediaTypeList);
    }
}
