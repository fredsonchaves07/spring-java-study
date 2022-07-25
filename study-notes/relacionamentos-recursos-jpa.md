# Relacionamentos e mais recursos da JPA e Hibernate

## Mapeamento bidirecional

- O mapeamento bidirecional ocorre quando existe relação entre 2 entidades
- Necessitam de mapeamento em ambas as entidades
- O bidirecional em alguns casos pode ser uma má prática se nao for necessário sua utilização
- Para cada registro no banco de dados é realizado um 'SELECT'
- Temos o conceito de **Entidade possuidora** e **Entidade inverso**
    - Entidade possuidora: A tabela dessa entidade será possuidora da chave estrangeira
    - Entidade inverso: O atributo deve ser anotado e configurado com `mappedBy`

## Mapeando relacionamento bidirecional com `@OneToMany`

- Relacionamento de 1 para muitos
- Exemplo de entidade possuidora:

```java
    @ManyToOne
@JoinColumn(nullable = false)
private Cozinha cozinha;
```

- Exemplo de entidade inverso:

```java
    @OneToMany(mappedBy = "cozinha")
private List<Restaurante> restaurantes=new ArrayList<>();
```

- Para evitar problemas de serialização e importação circular, podemos utilizar a anotação `@JsonIgnore` no atributo

## Mapeando relacionamento com `@ManyToMany`

- Relacionamento de muitos para muitos
- Ao utilizar anotação é criado uma tabela associativa
- A anotação `@JoinColumn` é utilizada para definir o nome de tabela e o nome das colunas
- O nome da tabela é opcional

```java
      @ManyToMany
@JoinTable(
        name = "restaurante_forma_pagamento",
        joinColumns = @JoinColumn(name = "restaurante_id"),
        inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
)
private List<FormaPagamento> formaPagamentos=new ArrayList<>();
```

- Dependendo da quantidade de recursos pode não ser uma boa abordagem retornar essas informações em uma lista de todos
  os restaurantes por exemplo
- Tende a ter um playload muito grande

## Mapenando classes incorporáveis com @Embedded e @Embeddable

- Usamos esse recurso quando queremos componentizar uma "Entidade"
- É incorporado em alguma entidade
- Não cria tabela no banco de dados
- Não representa uma entidade

```java

@Embeddable
public class Endereco {
}
}
```

- Classe que usara a classe embedable

```java
   @Embedded
private Endereco endereco;
```