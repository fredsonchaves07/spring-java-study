INSERT INTO cozinha (id, nome) VALUES (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');

INSERT INTO estado (id, nome) VALUES (1, 'Minas Gerais');
INSERT INTO estado (id, nome) VALUES (2, 'São Paulo');
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