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