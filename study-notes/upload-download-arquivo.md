# Upload e download de arquivos com Spring

## Implementando com multipart/form-data

- Podemos utilizar o recurso do HTTP multipart/form-data por meio de chave/valor podemos definir as propriedades e os valores dela
- Exemplo de controlador que recebe o arquivo da foto

```java
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
    }
```

## Servindo arquivos de fotos pela API

- Devemos implementar um método para servir para o consumidor. Buscar do armazenamento e enviar pra API

```java
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    @Autowired
    private FotoStorageService fotoStorageService;

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
```

## Checando media type ao servir arquivos de fotos

- Exemplo de implementação para verificar