# Técnicas avançadas para o desenvolvimento de API's 

## Utilização de subrecursos

- Podemos utilizar o conceito de subrecurso do endpoint. Chamamos de granularidade grossa.
- É útil quando não queremos retornar um array dentro de um recurso
- Exemplo de utilização de um subrecurso `GET /restaurantes/1/produtos` retorna os produtos do restaurante 1

## Chatty API x Chunky API

- O Chatty refere-se a modelagem de recursos com granularidade fina
- O consumidor precisa fazer várias chamadas para obter um recurso em comum
- O Chunky refere-se a modelagem de recursos com granularidade grossa
- O consumidor faz uma chamada para obter os recursos solicitados
- Não existe uma modelagem correta. Ambas estratégias podem ser utilizadas dependendo de como consumidor vai obter as informações da API
- Ao utilizar a granulardade fina deve-se evitar problemas de inconsistência de dados. Quando algum recurso obrigatório não for cadastrar. Endereço de um restaurante por exemplo

## Objetos alterados fora da transação

- Quando temos alguma alteração no objeto antes do contexto de transação, o objeto é persistido no banco de dados
- Isso ocorre pois no contexto transacional temos a instrução `BEGIN` e `COMMIT` salvando as alterações do objeto
- O objeto só não é persistido no banco quando temos alguma exceção no contexto transacional pois é chamado a instrução `ROLLBACK`
- Também não ocorre a transação quando não chamados o método de salvar do repositório em classe de serviço sem o contexto transacional `@Transactional`
- Nessa situação não ocorre as instruções de transação e o método do repositório não é invocado

## Implementando regra de negócio para não permitir cadastro de emails duplicados

- Devido a possibilidade de objeto ser alterado fora da transação, pode ocorrer erro quando tentamos atualizar um email de usuário já existente
- Para evitar isto, podemos informar ao JPA que não gerencie o estado do objeto

```java
public class CadastroUsuarioService() {

    @Autowired
    private EntityManager manager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        manager.detach(usuario);
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && usuarioExistente.get().equals(usuario)) {
            throw Exception("Email já sendo usado");
    }
}
```

- O comando `detach(usuario)` faz com que o JPA não gerencie o estado do objeto
- Uma outra possível solução é colocar o método `detach()` em um repositório customizado `CustomJPARepository`