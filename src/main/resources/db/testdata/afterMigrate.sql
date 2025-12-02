set foreign_key_checks = 0;

delete from status_historico;
delete from item_pedido;
delete from pedido;
delete from produto;
delete from cliente;

set foreign_key_checks = 1;

alter table cliente auto_increment = 1;
alter table produto auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;
alter table status_historico auto_increment = 1;


insert into cliente (id, nome, email, cpf, telefone, cep, logradouro, numero, complemento, bairro, cidade, estado, ativo)
values
(1, 'João da Silva', 'joao.silva@example.com', '12345678901', '34999990000', '38400111', 'Rua A', '100', null, 'Centro', 'Uberlândia', 'MG', true),
(2, 'Maria Oliveira', 'maria.oliveira@example.com', '98765432100', '11988887777', '01001000', 'Av Paulista', '1500', 'Ap 1201', 'Bela Vista', 'São Paulo', 'SP', true),
(3, 'Carlos Mendes', 'carlos.mendes@example.com', '45678912399', '85999998888', '60010010', 'Rua das Flores', '250', null, 'Centro', 'Fortaleza', 'CE', true);


insert into produto (id, nome, descricao, preco, estoque_disponivel, sku, categoria, ativo)
values
(1, 'Notebook Gamer', 'Notebook com GPU dedicada RTX', 6500.00, 10, 'NBK-GAMER-001', 'Eletrônicos', true),
(2, 'Mouse Sem Fio', 'Mouse ergonômico com 2400 DPI', 89.90, 50, 'MOUSE-SF-2400', 'Acessórios', true),
(3, 'Teclado Mecânico', 'Teclado RGB switch blue', 320.00, 30, 'TECL-MEC-RGB', 'Acessórios', true),
(4, 'Monitor 27"', 'Monitor IPS 144Hz', 1800.00, 15, 'MON-27-144', 'Eletrônicos', true);


insert into pedido (id, numero_pedido, cliente_id, status, valor_total, observacoes)
values
(1, 'PED-0001', 1, 'AGUARDANDO_PAGAMENTO', 6689.90, 'Entrega rápida, por favor'),
(2, 'PED-0002', 2, 'PAGAMENTO_CONFIRMADO', 2120.00, null),
(3, 'PED-0003', 3, 'CANCELADO', 89.90, 'Cliente desistiu');

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, subtotal)
values
(1, 1, 1, 1, 6500.00, 6500.00),
(2, 1, 2, 2, 89.90, 179.80),
(3, 2, 4, 1, 1800.00, 1800.00),
(4, 2, 3, 1, 320.00, 320.00);
