INSERT INTO cozinha (id, nome)
VALUES (1, 'Tailandesa');
INSERT INTO cozinha (id, nome)
VALUES (2, 'Indiana');
insert into cozinha (id, nome)
values (3, 'Argentina');
insert into cozinha (id, nome)
values (4, 'Brasileira');

INSERT INTO estado (id, nome)
VALUES (1, 'Minas Gerais');
INSERT INTO estado (id, nome)
VALUES (2, 'São Paulo');
INSERT INTO estado (id, nome)
VALUES (3, 'Ceará');

INSERT INTO cidade (id, nome, estado_id)
VALUES (1, 'Uberlândia', 1);
INSERT INTO cidade (id, nome, estado_id)
VALUES (2, 'Belo Horizonte', 1);
INSERT INTO cidade (id, nome, estado_id)
VALUES (3, 'São Paulo', 2);
INSERT INTO cidade (id, nome, estado_id)
VALUES (4, 'Campinas', 2);
INSERT INTO cidade (id, nome, estado_id)
VALUES (5, 'Fortaleza', 3);

INSERT INTO restaurante(nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro,
                        endereco_numero, endereco_bairro, data_cadastro, data_atualizacao)
VALUES ('Thai Goumert', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', now(), now());
INSERT INTO restaurante(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao)
VALUES ('Thai Delivery', 9.50, 1, now(), now());
INSERT INTO restaurante(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao)
VALUES ('Tuk Tuk Comida Indiana', 15, 2, now(), now());
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao)
values (4, 'Java Steakhouse', 12, 3, now(), now());
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao)
values (5, 'Lanchonete do Tio Sam', 11, 4, now(), now());
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao)
values (6, 'Bar da Maria', 6, 4, now(), now());


INSERT INTO forma_pagamento (id, descricao)
VALUES (1, 'Cartão de crédito');
INSERT INTO forma_pagamento (id, descricao)
VALUES (2, 'Cartão de débito');
INSERT INTO forma_pagamento (id, descricao)
VALUES (3, 'Dinheiro');

INSERT INTO permissao (id, nome, descricao)
VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO permissao (id, nome, descricao)
VALUES (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

INSERT INTO restaurante_forma_pagamento (restaurante_id, pagamento_id)
values (1, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, pagamento_id)
values (1, 2);
INSERT INTO restaurante_forma_pagamento (restaurante_id, pagamento_id)
values (1, 3);
INSERT INTO restaurante_forma_pagamento (restaurante_id, pagamento_id)
values (2, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, pagamento_id)
values (2, 2);
INSERT INTO restaurante_forma_pagamento (restaurante_id, pagamento_id)
values (2, 3);
INSERT INTO restaurante_forma_pagamento (restaurante_id, pagamento_id)
values (3, 1);
INSERT INTO restaurante_forma_pagamento (restaurante_id, pagamento_id)
values (3, 2);
INSERT INTO restaurante_forma_pagamento (restaurante_id, pagamento_id)
values (3, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 3),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 2),
       (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Salada picante com carne grelhada',
        'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20,
        1, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé',
        79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('T-Bone',
        'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89,
        1, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);
