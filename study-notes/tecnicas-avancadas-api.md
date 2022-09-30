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
- 