-- =====================================================================
-- Modelo fisico - Sistema Automotivo: Gestao de Estoque de Veiculos
-- Banco: MySQL 8+
-- (Opcional: a aplicacao ja cria as tabelas sozinha com ddl-auto=update.
--  Use este script se quiser criar o banco manualmente.)
-- =====================================================================

CREATE DATABASE IF NOT EXISTS estoque_veiculos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE estoque_veiculos;

CREATE TABLE IF NOT EXISTS marcas (
    id    BIGINT       NOT NULL AUTO_INCREMENT,
    nome  VARCHAR(60)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_marcas_nome (nome)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS modelos (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    nome      VARCHAR(80)  NOT NULL,
    marca_id  BIGINT       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_modelos_marca FOREIGN KEY (marca_id) REFERENCES marcas (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS veiculos (
    id              BIGINT         NOT NULL AUTO_INCREMENT,
    modelo_id       BIGINT         NOT NULL,
    ano_fabricacao  INT            NOT NULL,
    cor             VARCHAR(30)    NOT NULL,
    preco           DECIMAL(12, 2) NOT NULL,
    quilometragem   INT            NOT NULL,
    tipo            VARCHAR(20)    NOT NULL,  -- CARRO, MOTO, SUV, PICAPE, CAMINHAO
    status          VARCHAR(20)    NOT NULL,  -- DISPONIVEL, RESERVADO, VENDIDO, DESCONTINUADO
    PRIMARY KEY (id),
    CONSTRAINT fk_veiculos_modelo FOREIGN KEY (modelo_id) REFERENCES modelos (id),
    INDEX idx_veiculos_preco (preco),
    INDEX idx_veiculos_ano (ano_fabricacao),
    INDEX idx_veiculos_status (status)
) ENGINE = InnoDB;

-- ---------------------------------------------------------------------
-- Dados de exemplo (opcionais, para a demonstracao)
-- ---------------------------------------------------------------------
INSERT INTO marcas (nome) VALUES ('Toyota'), ('Honda'), ('Volkswagen'), ('Chevrolet');

INSERT INTO modelos (nome, marca_id) VALUES
    ('Corolla', 1), ('Hilux', 1),
    ('Civic', 2), ('CB 500', 2),
    ('Gol', 3), ('T-Cross', 3),
    ('Onix', 4), ('S10', 4);

INSERT INTO veiculos (modelo_id, ano_fabricacao, cor, preco, quilometragem, tipo, status) VALUES
    (1, 2022, 'Prata',    135000.00, 28000, 'CARRO',  'DISPONIVEL'),
    (2, 2021, 'Branco',   230000.00, 45000, 'PICAPE', 'DISPONIVEL'),
    (3, 2020, 'Preto',    118000.00, 52000, 'CARRO',  'RESERVADO'),
    (4, 2023, 'Vermelho',  42000.00,  8000, 'MOTO',   'DISPONIVEL'),
    (5, 2019, 'Branco',    39000.00, 71000, 'CARRO',  'VENDIDO'),
    (6, 2022, 'Cinza',    125000.00, 30000, 'SUV',    'DISPONIVEL'),
    (7, 2021, 'Azul',      72000.00, 40000, 'CARRO',  'DISPONIVEL'),
    (8, 2020, 'Preto',    185000.00, 60000, 'PICAPE', 'DISPONIVEL');
