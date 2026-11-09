-- IMPORTANTE: antes, execute 00_criar_banco.sql conectado ao banco postgres.
-- Depois conecte-se ao banco hell_market_simulator e execute TODO este arquivo.
-- ========================= TABELAS =========================

CREATE TABLE jogadores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    dinheiro NUMERIC(12,2) NOT NULL DEFAULT 500.00 CHECK (dinheiro >= 0),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE partidas (
    id SERIAL PRIMARY KEY,
    jogador_id INTEGER NOT NULL REFERENCES jogadores(id) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL DEFAULT 'EM_ANDAMENTO'
        CHECK (status IN ('EM_ANDAMENTO', 'VITORIA', 'DERROTA')),
    data_inicio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_fim TIMESTAMP
);

CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL UNIQUE,
    categoria VARCHAR(60) NOT NULL,
    preco NUMERIC(10,2) NOT NULL CHECK (preco >= 0),
    tem_validade BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE estoque (
    id SERIAL PRIMARY KEY,
    produto_id INTEGER NOT NULL UNIQUE REFERENCES produtos(id) ON DELETE CASCADE,
    quantidade INTEGER NOT NULL DEFAULT 0 CHECK (quantidade >= 0)
);

CREATE TABLE contratos (
    id SERIAL PRIMARY KEY,
    demonio VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    recompensa NUMERIC(12,2) NOT NULL CHECK (recompensa >= 0),
    dificuldade VARCHAR(20) NOT NULL DEFAULT 'FACIL',
    concluido BOOLEAN NOT NULL DEFAULT FALSE,
    contrato_final BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE contrato_produtos (
    id SERIAL PRIMARY KEY,
    contrato_id INTEGER NOT NULL REFERENCES contratos(id) ON DELETE CASCADE,
    produto_id INTEGER NOT NULL REFERENCES produtos(id),
    quantidade INTEGER NOT NULL CHECK (quantidade > 0),
    UNIQUE (contrato_id, produto_id)
);

CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    partida_id INTEGER NOT NULL REFERENCES partidas(id) ON DELETE CASCADE,
    contrato_id INTEGER NOT NULL REFERENCES contratos(id),
    status VARCHAR(20) NOT NULL DEFAULT 'ACEITO'
        CHECK (status IN ('ACEITO', 'CONCLUIDO', 'CANCELADO')),
    data_aceite TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_conclusao TIMESTAMP
);

CREATE TABLE pedido_itens (
    id SERIAL PRIMARY KEY,
    pedido_id INTEGER NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    produto_id INTEGER NOT NULL REFERENCES produtos(id),
    quantidade INTEGER NOT NULL CHECK (quantidade > 0)
);

CREATE TABLE upgrades (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT NOT NULL,
    custo NUMERIC(12,2) NOT NULL CHECK (custo >= 0),
    comprado BOOLEAN NOT NULL DEFAULT FALSE
);

-- ========================= DADOS INICIAIS =========================

INSERT INTO produtos (nome, categoria, preco, tem_validade) VALUES
('Tridente de Ferro Infernal', 'Armamentos', 80.00, FALSE),
('Água do Mal', 'Armamentos', 15.00, TRUE),
('Chicote Espinho de Metal de Sangue', 'Armamentos', 65.00, FALSE),
('Escudo de Leviatã', 'Armamentos', 95.00, FALSE),
('Cabeça de Bode', 'Ritual/Ritos', 25.00, TRUE),
('Vela Preta', 'Ritual/Ritos', 8.00, FALSE),
('Cruz Invertida', 'Ritual/Ritos', 20.00, FALSE),
('Lágrimas de Inocentes', 'Ritual/Ritos', 40.00, TRUE),
('Sangue de Pentagrama', 'Ritual/Ritos', 35.00, TRUE),
('Oferenda', 'Ritual/Ritos', 30.00, TRUE),
('Poção da Juventude Eterna', 'Pacto', 150.00, TRUE),
('Especiaria da Gula', 'Pacto', 55.00, TRUE),
('Poção de Ano de Vida', 'Pacto', 100.00, TRUE),
('Poção da Obsessão/Amor', 'Pacto', 90.00, TRUE);

INSERT INTO estoque (produto_id, quantidade)
SELECT id, 5 FROM produtos;

INSERT INTO contratos (demonio, descricao, recompensa, dificuldade) VALUES
('Belzebu', 'Entregue 2 unidades de Especiaria da Gula e 1 Oferenda.', 220.00, 'MEDIO'),
('Astaroth', 'Entregue 2 Velas Pretas e 1 Sangue de Pentagrama.', 140.00, 'FACIL'),
('Leviatã', 'Entregue 1 Escudo de Leviatã e 1 Tridente de Ferro Infernal.', 300.00, 'DIFICIL'),
('Mammon', 'Entregue 2 Poções de Ano de Vida.', 260.00, 'MEDIO'),
('Lúcifer', 'Contrato final: prepare o mercado para uma grande encomenda infernal.', 1000.00, 'FINAL');

-- Relaciona os produtos exigidos em cada contrato.
INSERT INTO contrato_produtos (contrato_id, produto_id, quantidade)
SELECT c.id, p.id, 2 FROM contratos c, produtos p
WHERE c.demonio='Belzebu' AND p.nome='Especiaria da Gula';
INSERT INTO contrato_produtos (contrato_id, produto_id, quantidade)
SELECT c.id, p.id, 1 FROM contratos c, produtos p
WHERE c.demonio='Belzebu' AND p.nome='Oferenda';
INSERT INTO contrato_produtos (contrato_id, produto_id, quantidade)
SELECT c.id, p.id, 2 FROM contratos c, produtos p
WHERE c.demonio='Astaroth' AND p.nome='Vela Preta';
INSERT INTO contrato_produtos (contrato_id, produto_id, quantidade)
SELECT c.id, p.id, 1 FROM contratos c, produtos p
WHERE c.demonio='Astaroth' AND p.nome='Sangue de Pentagrama';
INSERT INTO contrato_produtos (contrato_id, produto_id, quantidade)
SELECT c.id, p.id, 1 FROM contratos c, produtos p
WHERE c.demonio='Leviatã' AND p.nome='Escudo de Leviatã';
INSERT INTO contrato_produtos (contrato_id, produto_id, quantidade)
SELECT c.id, p.id, 1 FROM contratos c, produtos p
WHERE c.demonio='Leviatã' AND p.nome='Tridente de Ferro Infernal';
INSERT INTO contrato_produtos (contrato_id, produto_id, quantidade)
SELECT c.id, p.id, 2 FROM contratos c, produtos p
WHERE c.demonio='Mammon' AND p.nome='Poção de Ano de Vida';

INSERT INTO upgrades (nome, descricao, custo) VALUES
('Prateleiras Reforçadas', 'Aumenta a capacidade de armazenamento.', 200.00),
('Conservação Infernal', 'Reduz perdas de produtos com validade.', 350.00),
('Atendimento Expresso', 'Melhora a velocidade de atendimento dos pedidos.', 500.00),
('Estoque Eterno', 'Desbloqueia a melhoria de validade permanente.', 800.00);
