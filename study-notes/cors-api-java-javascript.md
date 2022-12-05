# CORS e consumo da API com Javascript e Java

## O que � CORS?

- O CORS � um recurso de seguran�a aplicado nos navegadores que evita as requisi��es cruzadas
- Necess�rio definir permiss�es para origem cruzadas dos navegadores. Isso � realizado nos cabeca�hos HTTP

## Habilitando CORS no controlador

- Usamos a anota��o `@CrossOrigin` informando o valor do client que ser� liberado
- Por padr�o, o spring retorna o codstatus 403 caso a origem n�o esteja nas regras de libera��o
- Caso seja necess�rio liberar todas as origens usamos o valor `*`
- Essa anota��o pode ser usada nos m�todos como tamb�m nos controladores