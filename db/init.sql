-- Primeiro as tabelas que só possuem FKs para outras
DROP TABLE IF EXISTS eta_admin;
DROP TABLE IF EXISTS tarefa;
DROP TABLE IF EXISTS funcionario;
DROP TABLE IF EXISTS avisos;
DROP TABLE IF EXISTS relatorio;
DROP TABLE IF EXISTS uso_produto;
DROP TABLE IF EXISTS estoque;

-- Depois as tabelas intermediárias
DROP TABLE IF EXISTS processo;
DROP TABLE IF EXISTS prioridade;
DROP TABLE IF EXISTS produto;

-- Agora as tabelas principais
DROP TABLE IF EXISTS eta;
DROP TABLE IF EXISTS cargo;
DROP TABLE IF EXISTS endereco;

-- ==============================
-- Tabela: Cargo
-- ==============================
CREATE TABLE cargo (
                       id_cargo SERIAL PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL UNIQUE,
                       acesso VARCHAR(100) NOT NULL
);

-- ==============================
-- Tabela: Endereco
-- ==============================
CREATE TABLE endereco (
                          id_endereco SERIAL PRIMARY KEY,
                          numero VARCHAR(10),
                          bairro VARCHAR(100),
                          cidade VARCHAR(100) NOT NULL,
                          estado CHAR(2) NOT NULL CHECK (estado ~ '^[A-Z]{2}$'),
    cep CHAR(8) CHECK (cep ~ '^[0-9]{8}$')
);

-- ==============================
-- Tabela: ETA (sem campo responsavel)
-- ==============================
CREATE TABLE eta (
                     id_eta SERIAL PRIMARY KEY,
                     nome VARCHAR(100) NOT NULL UNIQUE,
                     telefone VARCHAR(20),
                     capacidade_tratamento NUMERIC(12,2) CHECK (capacidade_tratamento > 0),
                     id_endereco INT NOT NULL,
                     CONSTRAINT fk_eta_endereco FOREIGN KEY (id_endereco) REFERENCES endereco (id_endereco)
);

-- ==============================
-- Tabela: ETA_Admin
-- ==============================
CREATE TABLE eta_admin (
                           id_eta_admin SERIAL PRIMARY KEY,
                           id_eta INT NOT NULL,
                           nome_admin VARCHAR(100) NOT NULL,
                           CONSTRAINT fk_etaadmin_eta FOREIGN KEY (id_eta) REFERENCES eta (id_eta) ON DELETE CASCADE
);

-- ==============================
-- Tabela: Funcionário
-- ==============================
CREATE TABLE funcionario (
                             id_funcionario SERIAL PRIMARY KEY,
                             nome VARCHAR(100) NOT NULL,
                             email VARCHAR(150) UNIQUE NOT NULL,
                             senha VARCHAR(255) NOT NULL, -- ADICIONADO para autenticação
                             data_admissao DATE NOT NULL DEFAULT CURRENT_DATE,
                             data_nascimento DATE CHECK (data_nascimento <= CURRENT_DATE),
                             id_eta INT,
                             id_cargo INT NOT NULL,
                             CONSTRAINT fk_func_eta FOREIGN KEY (id_eta) REFERENCES eta (id_eta),
                             CONSTRAINT fk_func_cargo FOREIGN KEY (id_cargo) REFERENCES cargo (id_cargo)
);

-- ==============================
-- Tabela: Prioridade
-- ==============================
CREATE TABLE prioridade (
                            id_prioridade SERIAL PRIMARY KEY,
                            nivel VARCHAR(50) NOT NULL
);

-- ==============================
-- Tabela: Tarefa
-- ==============================
CREATE TABLE tarefa (
                        id_tarefa SERIAL PRIMARY KEY,
                        descricao TEXT NOT NULL,
                        data_criacao DATE NOT NULL DEFAULT CURRENT_DATE,
                        data_conclusao DATE,
                        status VARCHAR(30) CHECK (status IN ('pendente','concluida')),
                        id_prioridade INT NOT NULL,
                        id_funcionario INT,
                        CONSTRAINT fk_tarefa_prioridade FOREIGN KEY (id_prioridade) REFERENCES prioridade (id_prioridade),
                        CONSTRAINT fk_tarefa_func FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario),
                        CONSTRAINT chk_data_tarefa CHECK (data_conclusao IS NULL OR data_conclusao >= data_criacao)
);

-- ==============================
-- Tabela: Avisos
-- ==============================
CREATE TABLE avisos (
                        id_avisos SERIAL PRIMARY KEY,
                        descricao TEXT NOT NULL,
                        data_ocorrencia DATE NOT NULL DEFAULT CURRENT_DATE,
                        id_eta INT NOT NULL,
                        id_prioridade INT NOT NULL,
                        CONSTRAINT fk_aviso_eta FOREIGN KEY (id_eta) REFERENCES eta (id_eta),
                        CONSTRAINT fk_aviso_prioridade FOREIGN KEY (id_prioridade) REFERENCES prioridade (id_prioridade)
);

-- ==============================
-- Tabela: Processo
-- ==============================
CREATE TABLE processo (
                          id_processo SERIAL PRIMARY KEY,
                          tipo_agua VARCHAR(50) NOT NULL,
                          volume_dagua_inicio NUMERIC(12,2) CHECK (volume_dagua_inicio >= 0),
                          ph_inicial NUMERIC(4,2) CHECK (ph_inicial BETWEEN 0 AND 14),
                          turbidez_inicial NUMERIC(6,2) CHECK (turbidez_inicial >= 0),
                          data_processo_inicial DATE NOT NULL DEFAULT CURRENT_DATE,
                          id_eta INT NOT NULL,
                          CONSTRAINT fk_processo_eta FOREIGN KEY (id_eta) REFERENCES ETA (id_eta)
);

-- ==============================
-- Tabela: Relatório
-- ==============================
CREATE TABLE relatorio (
                           id_relatorio SERIAL PRIMARY KEY,
                           volume_dagua_final NUMERIC(12,2) CHECK (volume_dagua_final >= 0),
                           ph_final NUMERIC(4,2) CHECK (ph_final BETWEEN 0 AND 14),
                           turbidez_final NUMERIC(6,2) CHECK (turbidez_final >= 0),
                           data_processo_final DATE NOT NULL,
                           id_processo INT NOT NULL,
                           CONSTRAINT fk_relatorio_processo FOREIGN KEY (id_processo) REFERENCES processo (id_processo)
);

-- ==============================
-- Tabela: Produto
-- ==============================
CREATE TABLE produto (
                         id_produto SERIAL PRIMARY KEY,
                         nome_produto VARCHAR(100) NOT NULL UNIQUE,
                         tipo VARCHAR(50),
                         unidade_medida VARCHAR(20) NOT NULL
);

-- ==============================
-- Tabela: Uso_produto (N:N)
-- ==============================
CREATE TABLE uso_produto (
                             id_uso_produto SERIAL PRIMARY KEY,
                             quantidade NUMERIC(12,2) CHECK (quantidade > 0),
                             id_processo INT NOT NULL,
                             id_produto INT NOT NULL,
                             CONSTRAINT fk_usoprod_processo FOREIGN KEY (id_processo) REFERENCES processo (id_processo),
                             CONSTRAINT fk_usoprod_produto FOREIGN KEY (id_produto) REFERENCES produto (id_produto)
);

-- ==============================
-- Tabela: Estoque
-- ==============================
CREATE TABLE estoque (
                         id_estoque SERIAL PRIMARY KEY,
                         quantidade NUMERIC(12,2) CHECK (quantidade >= 0),
                         id_produto INT NOT NULL,
                         id_eta INT NOT NULL,
                         CONSTRAINT fk_estoque_prod FOREIGN KEY (id_produto) REFERENCES produto (id_produto),
                         CONSTRAINT fk_estoque_eta FOREIGN KEY (id_eta) REFERENCES eta (id_eta)
);


-- ==============================
-- Cargo
-- ==============================
INSERT INTO cargo (nome, acesso) VALUES
                                     ('Administrador Master', 'total, pagamento, dev'),
                                     ('Gestor de Operação', 'total menos pagamento, controla estoque'),
                                     ('Operador', 'calculadora, ver estoque e relatórios'),
                                     ('Engenheiro Responsável', 'mesmo que operador, assinatura virtual relatórios');

-- ==============================
-- Endereco
-- ==============================
INSERT INTO endereco (numero, bairro, cidade, estado, cep) VALUES
                                                               ('123', 'Centro', 'São Paulo', 'SP', '01001000'),
                                                               ('45', 'Jardim América', 'São Paulo', 'SP', '01428000'),
                                                               ('78', 'Boa Vista', 'Campinas', 'SP', '13010100'),
                                                               ('22', 'Vila Mariana', 'São Paulo', 'SP', '04123010'),
                                                               ('50', 'Taquaral', 'Campinas', 'SP', '13060500');

-- ==============================
-- ETA
-- ==============================
INSERT INTO eta (nome, telefone, capacidade_tratamento, id_endereco) VALUES
                                                                         ('ETA São Paulo', '1133334444', 5000, 1),
                                                                         ('ETA Campinas', '1933335555', 3000, 3),
                                                                         ('ETA Rio Claro', '1944446666', 2000, 2),
                                                                         ('ETA Vila Mariana', '1133337777', 4000, 4),
                                                                         ('ETA Taquaral', '1933338888', 2500, 5);

-- ==============================
-- ETA_Admin
-- ==============================
INSERT INTO eta_admin (id_eta, nome_admin) VALUES
                                               (1, 'Carlos Silva'),
                                               (1, 'Maria Oliveira'),
                                               (2, 'João Santos'),
                                               (3, 'Ana Costa'),
                                               (4, 'Pedro Frossard');

-- ==============================
-- Funcionário
-- (senha será salva já com hash bcrypt no Spring Boot)
-- ==============================
INSERT INTO funcionario (nome, email, senha, data_admissao, data_nascimento, id_eta, id_cargo) VALUES
                                                                                                   ('Lucas Pereira', 'lucas@email.com', '$2a$10$1234567890abcdef', '2023-01-10', '1995-05-20', 1, 1),
                                                                                                   ('Fernanda Lima', 'fernanda@email.com', '$2a$10$abcdef1234567890', '2022-06-15', '1990-12-12', 1, 2),
                                                                                                   ('Rafael Souza', 'rafael@email.com', '$2a$10$9876543210fedcba', '2024-03-01', '1998-07-30', 2, 3),
                                                                                                   ('Camila Alves', 'camila@email.com', '$2a$10$qwertyuiopasdfgh', '2021-09-20', '1988-11-11', 3, 4),
                                                                                                   ('Bruno Dias', 'bruno@email.com', '$2a$10$zxcvbnmlkjhgfdsa', '2023-05-05', '1992-08-15', 4, 2);

-- ==============================
-- Prioridade
-- ==============================
INSERT INTO prioridade (nivel) VALUES
                                   ('Alta'),
                                   ('Média'),
                                   ('Baixa'),
                                   ('Urgente'),
                                   ('Normal');

-- ==============================
-- Tarefa
-- ==============================
INSERT INTO tarefa (descricao, status, id_prioridade) VALUES
                                                          ('Verificar qualidade da água', 'pendente', 1),
                                                          ('Atualizar relatório diário', 'pendente', 2),
                                                          ('Manutenção dos filtros', 'concluida', 3),
                                                          ('Controle de estoque de produtos químicos', 'pendente', 4),
                                                          ('Assinar relatórios completos', 'pendente', 5);

-- ==============================
-- Avisos
-- ==============================
INSERT INTO avisos (descricao, id_eta, id_prioridade) VALUES
                                                          ('Nível de cloro baixo', 1, 1),
                                                          ('Problema na bomba', 2, 2),
                                                          ('Revisão de segurança', 3, 3),
                                                          ('Teste de ph fora do padrão', 4, 4),
                                                          ('Falha no sistema de monitoramento', 5, 5);

-- ==============================
-- Processo
-- ==============================
INSERT INTO processo (tipo_agua, volume_dagua_inicio, ph_inicial, turbidez_inicial, id_eta) VALUES
                                                                                                ('Potável', 1000, 7.2, 5, 1),
                                                                                                ('Resíduos', 500, 6.8, 10, 2),
                                                                                                ('Potável', 800, 7.0, 3, 3),
                                                                                                ('Potável', 600, 7.1, 4, 4),
                                                                                                ('Resíduos', 400, 6.9, 6, 5);

-- ==============================
-- Relatorio
-- ==============================
INSERT INTO relatorio (volume_dagua_final, ph_final, turbidez_final, data_processo_final, id_processo) VALUES
                                                                                                           (950, 7.1, 4.5, '2025-08-22', 1),
                                                                                                           (480, 6.9, 9.0, '2025-08-21', 2),
                                                                                                           (790, 7.0, 2.5, '2025-08-22', 3),
                                                                                                           (580, 7.0, 3.5, '2025-08-22', 4),
                                                                                                           (390, 6.8, 5.5, '2025-08-22', 5);

-- ==============================
-- Produto
-- ==============================
INSERT INTO produto (nome_produto, tipo, unidade_medida) VALUES
                                                             ('Cloro', 'Químico', 'kg'),
                                                             ('Sulfato de Alumínio', 'Químico', 'kg'),
                                                             ('Filtro de Areia', 'Equipamento', 'unidade'),
                                                             ('Floculante', 'Químico', 'kg'),
                                                             ('Bomba', 'Equipamento', 'unidade');

-- ==============================
-- Uso_produto
-- ==============================
INSERT INTO uso_produto (quantidade, id_processo, id_produto) VALUES
                                                                  (5, 1, 1),
                                                                  (2, 2, 2),
                                                                  (1, 3, 3),
                                                                  (3, 4, 4),
                                                                  (1, 5, 5);

-- ==============================
-- Estoque
-- ==============================
INSERT INTO estoque (quantidade, id_produto, id_eta) VALUES
                                                         (50, 1, 1),
                                                         (30, 2, 2),
                                                         (10, 3, 3),
                                                         (25, 4, 4),
                                                         (5, 5, 5);

-- ==============================
-- DROP DA FUNCTION PARA GARANTIR QUE NÃO HAVERÁ ERROS
-- ==============================

DROP FUNCTION relatorio_funcionarios(INT);

-- ==============================
-- Relatório de funcionários com suas tarefas (Func)
-- ==============================
CREATE OR REPLACE FUNCTION relatorio_funcionarios(p_id_funcionario INT)
RETURNS TABLE(
    nome VARCHAR,
    email VARCHAR,
    data_admissao DATE,
    data_nascimento DATE,
    eta VARCHAR,
    cargo VARCHAR,
    descricao_tarefa text,
    status_tarefa VARCHAR
) AS $$
BEGIN
RETURN QUERY
SELECT f.nome, f.email, f.data_admissao, f.data_nascimento,
       e.nome AS eta, c.nome AS cargo,
       t.descricao AS descricao_tarefa,
       t.status AS status_tarefa
FROM funcionario f
         JOIN eta e ON f.id_eta = e.id_eta
         JOIN cargo c ON f.id_cargo = c.id_cargo
         LEFT JOIN tarefa t ON f.id_funcionario = t.id_funcionario
WHERE f.id_funcionario = p_id_funcionario;
END;
$$ LANGUAGE plpgsql;
