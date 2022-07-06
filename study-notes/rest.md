# REST com Spring

## Constraints (Boas práticas)

- Cliente-servidor
- Stateless
- Cache
- Interface uniforme
- Sistema em camadas
- Código sob demanda

## REST x RESTful

- A principal diferênca é conceitual
- REST -> Estilo arquitetural
- RESTful -> Implementação das constraints (Boas práticas) do REST. Ou seja, o RESTful é o termo usado quando todas as
  regras e boas práticas do REST foram implementadas

## Protocolo HTTP

- Conjunto de regras que define a comunicação entre páginas web!
  ![Captura de tela de 2022-07-04 18-00-18](https://user-images.githubusercontent.com/43495376/177216146-954a2ca8-21cc-40ce-97a7-7f8b195e856b.png)
- As requisições são compostas pelos seguintes elementos
  ![Captura de tela de 2022-07-04 18-01-43](https://user-images.githubusercontent.com/43495376/177216200-cbb58d39-9da1-4a91-ad3b-d5cdbac51ea4.png)

## Criando componente resource com spring

- Para definir um componente do tipo REST defnimos a anotação `@RestController`
- Exemplo de utilização de um controlador que retornará as informações de cozinhas

```java

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @GetMapping
  public List<Cozinha> listas() {
    return cozinhaRepository.listar();
  }
}
```

## Buscando recurso por parâmetro

- Exemplo de busca de um recurso por id

```java
    @GetMapping("/{id}")
public Cozinha buscar(@PathVariable Long id){
        return cozinhaRepository.buscarPorId(id);
        }
```

## Buscando um recurso que não existe

- Por padrão, o Spring devolve um status 200 mesmo quando um recurso não existe no banco de dados
- Podemos manipular a resposta usando `ResponseEntity`

```java
    @GetMapping("/{id}")
public ResponseEntity<Cozinha> buscar(@PathVariable Long id){
        Cozinha cozinha=cozinhaRepository.buscarPorId(id);
        if(cozinha!=null){
        return ResponseEntity.ok(cozinha);
        }
        return ResponseEntity.notFound().build();
        }
```

## Adicionando um recurso

- Usamos o método `POST` para realizar a inclusão de um recurso no banco de dados
- Exemplo de utilização

```java
    @PostMapping()
@ResponseStatus(HttpStatus.CREATED)
public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cozinhaRepository.salvar(cozinha);
        }
```