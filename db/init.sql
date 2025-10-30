-- Primeiro as tabelas que só possuem FKs para outras
DROP TABLE IF EXISTS tarefa;
DROP TABLE IF EXISTS avisos;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS relatorio;
DROP TABLE IF EXISTS uso_produto;
DROP TABLE IF EXISTS estoque;
DROP TABLE IF EXISTS pagamento;

DROP TABLE IF EXISTS processo;
DROP TABLE IF EXISTS prioridade;
DROP TABLE IF EXISTS produto;
DROP TABLE IF EXISTS comentario_gerente;
DROP TABLE IF EXISTS eta_admin;
DROP TABLE IF EXISTS funcionario;
DROP TABLE IF EXISTS eta;
DROP TABLE IF EXISTS cargo;
DROP TABLE IF EXISTS endereco;

DROP TABLE IF EXISTS unidade_medida;
DROP TABLE IF EXISTS plano;
DROP TABLE IF EXISTS log_geral;

DROP TRIGGER IF EXISTS trg_log_automatico ON eta_admin;
DROP TRIGGER IF EXISTS trg_log_automatico_funcionario ON funcionario;
DROP TRIGGER IF EXISTS trg_log_automatico_tarefa ON tarefa;
DROP TRIGGER IF EXISTS trg_log_automatico_avisos ON avisos;
DROP TRIGGER IF EXISTS trg_log_automatico_processo ON processo;
DROP TRIGGER IF EXISTS trg_log_automatico_relatorio ON relatorio;
DROP TRIGGER IF EXISTS trg_log_automatico_uso_produto ON uso_produto;
DROP TRIGGER IF EXISTS trg_log_automatico_estoque ON estoque;
DROP TRIGGER IF EXISTS trg_log_automatico_prioridade ON prioridade;
DROP TRIGGER IF EXISTS trg_log_automatico_produto ON produto;
DROP TRIGGER IF EXISTS trg_log_automatico_eta ON eta;
DROP TRIGGER IF EXISTS trg_log_automatico_cargo ON cargo;
DROP TRIGGER IF EXISTS trg_log_automatico_endereco ON endereco;

DROP FUNCTION IF EXISTS relatorio_funcionarios(id_funcionario INTEGER);
DROP FUNCTION IF EXISTS resumo_tarefas_usuario(p_id_funcionario INT);
DROP FUNCTION IF EXISTS resumo_tarefas_eta(p_id_eta INT);
DROP FUNCTION IF EXISTS gerar_relatorio_mes(mes INT, ano INT);
DROP FUNCTION IF EXISTS produtos_usados_mes(mes INT, ano INT);
DROP FUNCTION IF EXISTS log_automatico();
DROP FUNCTION IF EXISTS calcular_data_validade();
DROP FUNCTION IF EXISTS atualizar_estoque();
DROP PROCEDURE IF EXISTS adicionar_estoque(p_id_produto INT, p_id_eta INT, p_quantidade NUMERIC);
DROP PROCEDURE IF EXISTS tirar_estoque(p_id_produto INT, p_id_eta INT, p_quantidade NUMERIC);


-- ==============================
-- Tabela: Cargo
-- ==============================
CREATE TABLE cargo (
                       id_cargo SERIAL PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL UNIQUE,
                       acesso VARCHAR(50) NOT NULL
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
-- Tabela: Funcionário
-- ==============================
CREATE TABLE funcionario (
                             id_funcionario SERIAL PRIMARY KEY,
                             nome VARCHAR(100) NOT NULL,
                             email VARCHAR(150) UNIQUE NOT NULL,
                             data_admissao DATE NOT NULL DEFAULT CURRENT_DATE,
                             data_nascimento DATE CHECK (data_nascimento <= CURRENT_DATE),
                             id_eta INT,
                             id_cargo INT NOT NULL,
                             id_pai INT,
                             CONSTRAINT fk_func_eta FOREIGN KEY (id_eta) REFERENCES eta (id_eta),
                             CONSTRAINT fk_func_cargo FOREIGN KEY (id_cargo) REFERENCES cargo(id_cargo),
                             CONSTRAINT fk_func_pai FOREIGN KEY (id_pai) REFERENCES funcionario(id_funcionario)
);

-- ==============================
-- Tabela: ETA_Admin
-- ==============================
CREATE TABLE eta_admin (
                           id_eta_admin SERIAL PRIMARY KEY,
                           id_eta INT NOT NULL,
                           id_funcionario INT NOT NULL,
                           CONSTRAINT fk_etaadmin_eta FOREIGN KEY (id_eta) REFERENCES eta (id_eta),
                           CONSTRAINT fk_etaadmin_func FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario)
);


-- ==============================
-- Tabela: comentario_gerente
-- ==============================
CREATE TABLE comentario_gerente (
                                    id_comentario SERIAL PRIMARY KEY,
                                    comentario TEXT NOT NULL,
                                    ano_mes DATE NOT NULL,
                                    id_eta_admin INT NOT NULL,
                                    CONSTRAINT fk_comentario_etaadmin FOREIGN KEY (id_eta_admin) REFERENCES eta_admin (id_eta_admin)
);



-- ==============================
-- Tabela: Prioridade
-- ==============================
CREATE TABLE prioridade (
                            id_prioridade SERIAL PRIMARY KEY,
                            nivel VARCHAR(50) NOT NULL
);

-- ==============================
-- Tabela: Status
-- ==============================
CREATE TABLE status (
                        id_status SERIAL PRIMARY KEY,
                        status VARCHAR(50) NOT NULL UNIQUE
);

-- ==============================
-- Tabela: Tarefa
-- ==============================
CREATE TABLE tarefa (
                        id_tarefa SERIAL PRIMARY KEY,
                        descricao TEXT NOT NULL,
                        data_criacao DATE NOT NULL DEFAULT CURRENT_DATE,
                        data_conclusao DATE,
                        id_prioridade INT NOT NULL,
                        id_funcionario INT NOT NULL,
                        id_status INT,
                        CONSTRAINT fk_status_tarefa FOREIGN KEY (id_status) REFERENCES status (id_status),
                        CONSTRAINT fk_tarefa_prioridade FOREIGN KEY (id_prioridade) REFERENCES prioridade (id_prioridade),
                        CONSTRAINT chk_data_tarefa CHECK (data_conclusao IS NULL OR data_conclusao >= data_criacao),
                        CONSTRAINT fk_tarefa_func FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario)
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
                        id_status INT,
                        CONSTRAINT fk_status_avisos FOREIGN KEY (id_status) REFERENCES status (id_status),
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
                           data_processo_final DATE NOT NULL DEFAULT CURRENT_DATE,
                           id_processo INT NOT NULL UNIQUE,
                           CONSTRAINT fk_relatorio_processo FOREIGN KEY (id_processo) REFERENCES processo (id_processo)
);

-- ==============================
-- Tabela: unidade_medida
-- ==============================

CREATE TABLE unidade_medida (
                                id_unidade SERIAL PRIMARY KEY,
                                nome VARCHAR(50) CHECK ( nome = 'kilos' OR nome = 'litro' OR nome = 'unidade' ) NOT NULL
);

-- ==============================
-- Tabela: Produto
-- ==============================
CREATE TABLE produto (
                         id_produto SERIAL PRIMARY KEY,
                         nome_produto VARCHAR(100) NOT NULL UNIQUE,
                         tipo VARCHAR(50),
                         id_unidade INT NOT NULL,
                         CONSTRAINT fk_tp_unidade FOREIGN KEY (id_unidade) REFERENCES unidade_medida (id_unidade)
);



-- ==============================
-- Tabela: Uso_produto
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
                         max_estoque NUMERIC(12,2) CHECK (max_estoque >= 0),
                         id_produto INT NOT NULL,
                         id_eta INT NOT NULL,
                         CONSTRAINT fk_estoque_prod FOREIGN KEY (id_produto) REFERENCES produto (id_produto),
                         CONSTRAINT fk_estoque_eta FOREIGN KEY (id_eta) REFERENCES eta (id_eta)
);


-- ==============================
-- Tabela: Plano
-- ==============================
CREATE TABLE plano (
                       id_plano SERIAL PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL UNIQUE,
                       descricao TEXT,
                       valor_mensal NUMERIC(10,2) NOT NULL CHECK (valor_mensal >= 0),
                       duracao_meses INT NOT NULL CHECK (duracao_meses > 0)
);

-- ==============================
-- Tabela: Pagamento
-- ==============================
CREATE TABLE pagamento (
                           id_pagamento SERIAL PRIMARY KEY,
                           id_plano INT NOT NULL,
                           data_pagamento DATE NOT NULL DEFAULT CURRENT_DATE,
                           data_validade DATE NOT NULL,
                           id_eta_admin INT NOT NULL,
                           CONSTRAINT fk_pagamento_etaadmin FOREIGN KEY (id_eta_admin)
                               REFERENCES eta_admin (id_eta_admin),
                           CONSTRAINT fk_pagamento_plano FOREIGN KEY (id_plano)
                               REFERENCES Plano (id_plano)
);


CREATE TABLE log_geral (
                           id_log SERIAL PRIMARY KEY,
                           tabela varchar(50) NOT NULL,
                           operacao varchar(10) NOT NULL,
                           data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ==============================
-- Funções
-- ==============================

-- Função para log automático
CREATE OR REPLACE FUNCTION log_automatico() RETURNS TRIGGER AS $$
BEGIN
INSERT INTO log_geral (tabela, operacao)
VALUES (TG_TABLE_NAME, TG_OP);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Função para calcular validade do plano
CREATE OR REPLACE FUNCTION calcular_data_validade()
RETURNS TRIGGER AS $$
BEGIN
SELECT NEW.data_pagamento + (p.duracao_meses || ' months')::interval
INTO NEW.data_validade
FROM plano p
WHERE p.id_plano = NEW.id_plano;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Função para atualizar estoque
CREATE OR REPLACE FUNCTION atualizar_estoque()
RETURNS TRIGGER AS $$
DECLARE
v_id_eta INT;
    v_estoque NUMERIC;
BEGIN
SELECT id_eta INTO v_id_eta FROM processo WHERE id_processo = NEW.id_processo;
IF v_id_eta IS NULL THEN
        RAISE EXCEPTION 'Processo % não encontrado', NEW.id_processo;
END IF;

SELECT quantidade INTO v_estoque
FROM estoque
WHERE id_produto = NEW.id_produto
  AND id_eta = v_id_eta
    FOR UPDATE;

IF v_estoque IS NULL THEN
        RAISE EXCEPTION 'Produto % não está no estoque da ETA %', NEW.id_produto, v_id_eta;
    ELSIF v_estoque < NEW.quantidade THEN
        RAISE EXCEPTION 'Estoque insuficiente para o produto % na ETA %', NEW.id_produto, v_id_eta;
END IF;

UPDATE estoque
SET quantidade = quantidade - NEW.quantidade
WHERE id_produto = NEW.id_produto
  AND id_eta = v_id_eta;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION relatorio_funcionarios(p_id_funcionario INTEGER DEFAULT NULL)
RETURNS TABLE(
    id INTEGER,
    nome VARCHAR,
    email VARCHAR,
    data_admissao DATE,
    data_nascimento DATE,
    eta VARCHAR,
    cargo VARCHAR,
    id_tarefa INT,
    id_eta INT
) AS $$
BEGIN
    IF p_id_funcionario IS NOT NULL THEN
        RETURN QUERY
SELECT f.id_funcionario AS id,
       f.nome,
       f.email,
       f.data_admissao,
       f.data_nascimento,
       e.nome AS eta,
       c.nome AS cargo,
       t.id_tarefa,
       e.id_eta
FROM funcionario f
         JOIN eta e ON f.id_eta = e.id_eta
         JOIN cargo c ON f.id_cargo = c.id_cargo
         LEFT JOIN tarefa t ON f.id_funcionario = t.id_funcionario
         LEFT JOIN status s ON t.id_status = s.id_status
WHERE f.id_funcionario = p_id_funcionario;
ELSE
        RETURN QUERY
SELECT f.id_funcionario AS id,
       f.nome,
       f.email,
       f.data_admissao,
       f.data_nascimento,
       e.nome AS eta,
       c.nome AS cargo,
       t.id_tarefa,
       e.id_eta
FROM funcionario f
         JOIN eta e ON f.id_eta = e.id_eta
         JOIN cargo c ON f.id_cargo = c.id_cargo
         LEFT JOIN tarefa t ON f.id_funcionario = t.id_funcionario
         LEFT JOIN status s ON t.id_status = s.id_status;
END IF;
END;
$$ LANGUAGE plpgsql;

-- Uma função que retorne as tarefas do usuário
CREATE OR REPLACE FUNCTION resumo_tarefas_usuario(p_id_funcionario INT) RETURNS TABLE(
    tarefas_para_hoje INT,
    tarefas_feitas INT,
    tarefas_nao_realizadas INT,
    tarefas_totais INT
) AS $$
BEGIN
RETURN QUERY
SELECT
    COUNT(CASE WHEN t.data_conclusao = CURRENT_DATE THEN 1 END)::INT AS
        tarefas_para_hoje,
    COUNT(CASE WHEN t.id_status = 2 THEN 1 END)::INT AS tarefas_feitas,
    COUNT(CASE WHEN t.id_status != 2 THEN 1 END)::INT AS tarefas_nao_realizadas,
    COUNT(*)::INT AS tarefas_totais
FROM tarefa t
WHERE t.id_funcionario = p_id_funcionario;
END;
$$ LANGUAGE plpgsql;


-- Uma função que retorne as tarefas da eta
CREATE OR REPLACE FUNCTION resumo_tarefas_eta(p_id_eta INT) RETURNS TABLE(
    tarefas_para_hoje INT,
    tarefas_feitas INT,
    tarefas_nao_realizadas INT,
    tarefas_totais INT
) AS $$
BEGIN
RETURN QUERY
SELECT
    COUNT(CASE WHEN t.data_conclusao = CURRENT_DATE THEN 1 END)::INT AS
        tarefas_para_hoje,
    COUNT(CASE WHEN t.id_status = 2 THEN 1 END)::INT AS tarefas_feitas,
    COUNT(CASE WHEN t.id_status != 2 THEN 1 END)::INT AS tarefas_nao_realizadas,
    COUNT(*)::INT AS tarefas_totais
FROM tarefa t
         JOIN funcionario f ON t.id_funcionario = f.id_funcionario
WHERE f.id_eta = p_id_eta;
END;
$$ LANGUAGE plpgsql;


-- Relatório mensal de volume tratado por ETA
CREATE OR REPLACE FUNCTION gerar_relatorio_mes(mes INT, ano INT)
RETURNS TABLE(
    id_eta INT,
    volume_tratado NUMERIC,
    media_diaria NUMERIC,
    eta_nome VARCHAR,
    eta_admin VARCHAR,
    cidade VARCHAR,
    estado CHAR(2),
    bairro VARCHAR,
    ph_min NUMERIC,
    ph_max NUMERIC,
    comentario_gerente TEXT
) AS $$
BEGIN
RETURN QUERY
SELECT
    e.id_eta AS id_eta,
    SUM(r.volume_dagua_final) AS volume_tratado,
    SUM(r.volume_dagua_final) / 30 AS media_diaria,
    e.nome AS eta_nome,
    f.nome AS eta_admin, -- nome do funcionário administrador
    ender.cidade,
    ender.estado,
    ender.bairro,
    MIN(r.ph_final) AS ph_min,
    MAX(r.ph_final) AS ph_max,
    cg.comentario AS comentario_gerente
FROM relatorio r
         JOIN processo p ON r.id_processo = p.id_processo
         JOIN eta e ON p.id_eta = e.id_eta
         JOIN endereco ender ON e.id_endereco = ender.id_endereco
         JOIN eta_admin ea ON e.id_eta = ea.id_eta
         JOIN funcionario f ON ea.id_funcionario = f.id_funcionario
         LEFT JOIN comentario_gerente cg ON ea.id_eta_admin = cg.id_eta_admin
    AND DATE_TRUNC('month', cg.ano_mes) = MAKE_DATE(ano, mes, 1)
WHERE EXTRACT(MONTH FROM r.data_processo_final) = mes
  AND EXTRACT(YEAR FROM r.data_processo_final) = ano
GROUP BY e.id_eta, e.nome, f.nome, ender.cidade, ender.estado, ender.bairro, cg.comentario;
END;
$$ LANGUAGE plpgsql;


-- Relatório de produtos usados por mês
CREATE OR REPLACE FUNCTION produtos_usados_mes(mes INT, ano INT) RETURNS TABLE(
    nome_produto VARCHAR,
    eta_nome VARCHAR,
    total_usado NUMERIC
) AS $$
BEGIN
RETURN QUERY
SELECT
    pr.nome_produto,
    e.nome AS eta_nome,
    SUM(up.quantidade) AS total_usado
FROM uso_produto up
         JOIN produto pr ON up.id_produto = pr.id_produto
         JOIN processo p ON up.id_processo = p.id_processo
         JOIN eta e ON p.id_eta = e.id_eta
WHERE EXTRACT(MONTH FROM p.data_processo_inicial) = mes
  AND EXTRACT(YEAR FROM p.data_processo_inicial) = ano
GROUP BY pr.nome_produto, e.nome;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION organograma_eta(p_id_eta INT)
RETURNS TABLE(
    id_funcionario INT,
    nome_funcionario VARCHAR,
    cargo_funcionario VARCHAR,
    id_supervisor INT,
    nome_supervisor VARCHAR
) AS $$
BEGIN
RETURN QUERY
    WITH RECURSIVE hierarquia AS (
        SELECT
            f.id_funcionario,
            f.nome AS nome_funcionario,
            c.nome AS cargo_funcionario,
            f.id_pai AS id_supervisor,
            1 AS nivel
        FROM funcionario f
        JOIN cargo c ON f.id_cargo = c.id_cargo
        WHERE f.id_eta = p_id_eta AND f.id_pai IS NULL

        UNION ALL

        SELECT
            f.id_funcionario,
            f.nome AS nome_funcionario,
            c.nome AS cargo_funcionario,
            f.id_pai AS id_supervisor,
            h.nivel + 1 AS nivel
        FROM funcionario f
        JOIN cargo c ON f.id_cargo = c.id_cargo
        JOIN hierarquia h ON f.id_pai = h.id_funcionario
    )
SELECT
    h.id_funcionario,
    h.nome_funcionario,
    h.cargo_funcionario,
    h.id_supervisor,
    sup.nome AS nome_supervisor
FROM hierarquia h
         LEFT JOIN funcionario sup ON h.id_supervisor = sup.id_funcionario
ORDER BY h.nivel, h.nome_funcionario;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION verificar_relatorio_processo()
RETURNS TRIGGER AS $$
DECLARE
v_data_processo DATE;
BEGIN
SELECT data_processo_inicial
INTO v_data_processo
FROM processo
WHERE id_processo = NEW.id_processo;

IF NEW.data_processo_final < v_data_processo THEN
        RAISE EXCEPTION 'A data do relatório (%) não pode ser anterior ao início do processo (%)',
        NEW.data_processo_final, v_data_processo;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- Relatorio com comentario do gerente
CREATE OR REPLACE FUNCTION relatorio_comentario_gerente(p_id_eta INT, mes INT, ano INT)
RETURNS TABLE(
    id_relatorio INT,
    volume_dagua_final NUMERIC,
    ph_final NUMERIC,
    turbidez_final NUMERIC,
    data_processo_final DATE,
    comentario_gerente TEXT
) AS $$
BEGIN
RETURN QUERY
SELECT
    r.id_relatorio,
    r.volume_dagua_final,
    r.ph_final,
    r.turbidez_final,
    r.data_processo_final,
    cg.comentario AS comentario_gerente
FROM relatorio r
         JOIN processo p ON r.id_processo = p.id_processo
         JOIN eta_admin ea ON p.id_eta = ea.id_eta
         JOIN comentario_gerente cg ON ea.id_eta_admin = cg.id_eta_admin
    AND DATE_TRUNC('month', cg.ano_mes) = MAKE_DATE(ano, mes, 1)
WHERE p.id_eta = p_id_eta
  AND EXTRACT(MONTH FROM r.data_processo_final) = mes
  AND EXTRACT(YEAR FROM r.data_processo_final) = ano;
END;
$$ LANGUAGE plpgsql;


SELECT * FROM relatorio_comentario_gerente(1, 10, 2025);


-- Data relatorio por eta
CREATE OR REPLACE FUNCTION data_relatorio_eta(p_id_eta INT)
RETURNS TABLE(
    id_relatorio INT,
    data_processo_final DATE
) AS $$
BEGIN
RETURN QUERY
SELECT DISTINCT ON (EXTRACT(YEAR FROM r.data_processo_final), EXTRACT(MONTH FROM r.data_processo_final))
    r.id_relatorio,
    r.data_processo_final
FROM relatorio r
    JOIN processo p ON r.id_processo = p.id_processo
WHERE p.id_eta = p_id_eta
ORDER BY
    EXTRACT(YEAR FROM r.data_processo_final) DESC,
    EXTRACT(MONTH FROM r.data_processo_final) DESC,
    r.data_processo_final DESC;
END;
$$ LANGUAGE plpgsql;

-- ==============================
-- PROCEDURES
-- =============================

-- Procedure para adicionar produto ao estoque
CREATE OR REPLACE PROCEDURE adicionar_estoque(p_id_produto INT, p_id_eta INT, p_quantidade NUMERIC)
LANGUAGE plpgsql
AS $$
BEGIN
    IF p_quantidade <= 0 THEN
        RAISE EXCEPTION 'Quantidade deve ser maior que zero';
END IF;
    IF EXISTS (SELECT 1 FROM produto WHERE id_produto = p_id_produto) = FALSE THEN
        RAISE EXCEPTION 'Produto com ID % não existe', p_id_produto;
END IF;
    IF EXISTS (SELECT 1 FROM eta WHERE id_eta = p_id_eta) = FALSE THEN
        RAISE EXCEPTION 'ETA com ID % não existe', p_id_eta;
END IF;

    IF EXISTS (SELECT 1 FROM estoque WHERE id_produto = p_id_produto AND id_eta = p_id_eta) THEN
UPDATE estoque
SET quantidade = quantidade + p_quantidade
WHERE id_produto = p_id_produto AND id_eta = p_id_eta;
ELSE
        INSERT INTO estoque (id_produto, id_eta, quantidade, max_estoque)
        VALUES (p_id_produto, p_id_eta, p_quantidade, p_quantidade * 2);
END IF;
END;
$$;


-- Procedure para tirar produtos do estoque
CREATE OR REPLACE PROCEDURE tirar_estoque(p_id_produto INT, p_id_eta INT, p_quantidade NUMERIC)
LANGUAGE plpgsql
AS $$
DECLARE
v_estoque NUMERIC;
BEGIN
    IF p_quantidade <= 0 THEN
        RAISE EXCEPTION 'Quantidade deve ser maior que zero';
END IF;
    IF EXISTS (SELECT 1 FROM produto WHERE id_produto = p_id_produto) = FALSE THEN
        RAISE EXCEPTION 'Produto com ID % não existe', p_id_produto;
END IF;
    IF EXISTS (SELECT 1 FROM eta WHERE id_eta = p_id_eta) = FALSE THEN
        RAISE EXCEPTION 'ETA com ID % não existe', p_id_eta;
END IF;

SELECT quantidade INTO v_estoque
FROM estoque
WHERE id_produto = p_id_produto AND id_eta = p_id_eta;

IF v_estoque IS NULL THEN
        RAISE EXCEPTION 'Produto com ID % não está no estoque da ETA %', p_id_produto, p_id_eta;
    ELSIF v_estoque < p_quantidade THEN
        RAISE EXCEPTION 'Estoque insuficiente para o produto % na ETA %', p_id_produto, p_id_eta;
END IF;

UPDATE estoque
SET quantidade = quantidade - p_quantidade
WHERE id_produto = p_id_produto AND id_eta = p_id_eta;
END;
$$;

-- Procedure para adicionar tarefa
CREATE OR REPLACE PROCEDURE adicionar_tarefa(p_descricao TEXT, p_id_prioridade INT, p_id_funcionario INT, p_id_status INT DEFAULT NULL)
LANGUAGE plpgsql
AS $$
BEGIN
    IF p_descricao IS NULL OR LENGTH(TRIM(p_descricao)) = 0 THEN
        RAISE EXCEPTION 'Descrição não pode ser vazia';
END IF;
    IF EXISTS (SELECT 1 FROM prioridade WHERE id_prioridade = p_id_prioridade) = FALSE THEN
        RAISE EXCEPTION 'Prioridade com ID % não existe', p_id_prioridade;
END IF;
    IF EXISTS (SELECT 1 FROM funcionario WHERE id_funcionario = p_id_funcionario) = FALSE THEN
        RAISE EXCEPTION 'Funcionário com ID % não existe', p_id_funcionario;
END IF;
    IF p_id_status IS NOT NULL AND EXISTS (SELECT 1 FROM status WHERE id_status = p_id_status) = FALSE THEN
        RAISE EXCEPTION 'Status com ID % não existe', p_id_status;
END IF;

INSERT INTO tarefa (descricao, id_prioridade, id_funcionario, id_status)
VALUES (p_descricao, p_id_prioridade, p_id_funcionario, p_id_status);
END;
$$;

-- ==============================
-- Triggers
-- ==============================

-- Trigger para atualizar estoque ao usar produto
CREATE TRIGGER trg_atualizar_estoque
    BEFORE INSERT ON uso_produto
    FOR EACH ROW
    EXECUTE FUNCTION atualizar_estoque();


-- Trigger para calcular data de validade do plano no pagamento
CREATE TRIGGER trg_calcular_data_validade
    BEFORE INSERT ON pagamento
    FOR EACH ROW
    EXECUTE FUNCTION calcular_data_validade();


-- Trigger para verificar relatorio processo
CREATE TRIGGER trg_verificar_relatorio_processo
    BEFORE INSERT OR UPDATE ON relatorio
                         FOR EACH ROW
                         EXECUTE FUNCTION verificar_relatorio_processo();

-- ==============================
-- Triggers de log automático
-- ==============================

-- Trigger para eta_admin
CREATE TRIGGER trg_log_automatico
    AFTER INSERT OR UPDATE OR DELETE ON eta_admin
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para funcionário
CREATE TRIGGER trg_log_automatico_funcionario
    AFTER INSERT OR UPDATE OR DELETE ON funcionario
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para tarefa
CREATE TRIGGER trg_log_automatico_tarefa
    AFTER INSERT OR UPDATE OR DELETE ON tarefa
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para avisos
CREATE TRIGGER trg_log_automatico_avisos
    AFTER INSERT OR UPDATE OR DELETE ON avisos
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para processo
CREATE TRIGGER trg_log_automatico_processo
    AFTER INSERT OR UPDATE OR DELETE ON processo
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para relatorio
CREATE TRIGGER trg_log_automatico_relatorio
    AFTER INSERT OR UPDATE OR DELETE ON relatorio
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para uso_produto
CREATE TRIGGER trg_log_automatico_uso_produto
    AFTER INSERT OR UPDATE OR DELETE ON uso_produto
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para estoque
CREATE TRIGGER trg_log_automatico_estoque
    AFTER INSERT OR UPDATE OR DELETE ON estoque
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para prioridade
CREATE TRIGGER trg_log_automatico_prioridade
    AFTER INSERT OR UPDATE OR DELETE ON prioridade
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para produto
CREATE TRIGGER trg_log_automatico_produto
    AFTER INSERT OR UPDATE OR DELETE ON produto
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para eta
CREATE TRIGGER trg_log_automatico_eta
    AFTER INSERT OR UPDATE OR DELETE ON eta
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para cargo
CREATE TRIGGER trg_log_automatico_cargo
    AFTER INSERT OR UPDATE OR DELETE ON cargo
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para endereco
CREATE TRIGGER trg_log_automatico_endereco
    AFTER INSERT OR UPDATE OR DELETE ON endereco
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para unidade_medida
CREATE TRIGGER trg_log_automatico_unidade_medida
    AFTER INSERT OR UPDATE OR DELETE ON unidade_medida
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para plano
CREATE TRIGGER trg_log_automatico_plano
    AFTER INSERT OR UPDATE OR DELETE ON plano
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- Trigger para pagamento
CREATE TRIGGER trg_log_automatico_pagamento
    AFTER INSERT OR UPDATE OR DELETE ON pagamento
    FOR EACH ROW EXECUTE FUNCTION log_automatico();

-- =============================
-- INDEXES
-- =============================
CREATE INDEX idx_status_tarefa ON tarefa(id_status);
CREATE INDEX idx_status_avisos ON avisos(id_status);

-- ==============================
-- CARGO
-- ==============================
INSERT INTO cargo (nome, acesso) VALUES
                                     ('Administrador Master', 'total, pagamento, dev'),
                                     ('Gestor de Operação', 'total menos pagamento, controla estoque'),
                                     ('Operador', 'calculadora, ver estoque e relatórios'),
                                     ('Engenheiro Responsável', 'mesmo que operador, assinatura virtual relatórios');

-- ==============================
-- ENDEREÇO
-- ==============================
INSERT INTO endereco (numero, bairro, cidade, estado, cep) VALUES
                                                               ('123', 'Centro', 'São Paulo', 'SP', '01001000'),
                                                               ('45', 'Jardim América', 'São Paulo', 'SP', '01428000'),
                                                               ('78', 'Boa Vista', 'Campinas', 'SP', '13010100'),
                                                               ('22', 'Vila Mariana', 'São Paulo', 'SP', '04123010'),
                                                               ('50', 'Taquaral', 'Campinas', 'SP', '13060500'),
                                                               ('88', 'Centro', 'Rio Claro', 'SP', '13500000');  -- novo endereço para Rio Claro

-- ==============================
-- ETA
-- ==============================
INSERT INTO eta (nome, telefone, capacidade_tratamento, id_endereco) VALUES
                                                                         ('ETA São Paulo', '1133334444', 5000, 1),
                                                                         ('ETA Campinas', '1933335555', 3000, 3),
                                                                         ('ETA Rio Claro', '1944446666', 2000, 6),
                                                                         ('ETA Vila Mariana', '1133337777', 4000, 4),
                                                                         ('ETA Taquaral', '1933338888', 2500, 5);

-- ==============================
-- FUNCIONÁRIO
-- ==============================
INSERT INTO funcionario (nome, email, data_admissao, data_nascimento, id_eta, id_cargo, id_pai) VALUES
                                                                                                    ('Carlos Silva', 'carlos.silva@gmail.com', '2020-01-10', '1985-04-15', 1, 1, NULL),
                                                                                                    ('João Santos', 'joao.santos@gmail.com', '2021-03-12', '1990-08-25', 2, 1, NULL),
                                                                                                    ('Lucas Pereira', 'lucas.pereira@gmail.com', '2023-01-10', '1995-05-20', 1, 3, 1),
                                                                                                    ('Fernanda Lima', 'fernanda.lima@gmail.com', '2022-06-15', '1990-12-12', 1, 3, 1),
                                                                                                    ('Ana Costa', 'ana.costa@gmail.com', '2021-09-20', '1988-11-11', 3, 1, NULL),
                                                                                                    ('Pedro Frossard', 'pedro.frossard@gmail.com','2023-05-05', '1992-08-15', 1, 4, NULL),
                                                                                                    ('Bruno Dias', 'bruno.dias@gmail.com', '2023-07-11', '1993-02-10', 5, 1, NULL),
                                                                                                    ('Fernando Paiva', 'fernando.paiva@gmail.com', '2022-11-23', '1987-03-30', 4, 2, 6);

SELECT * FROM tarefa;

-- ==============================
-- ETA_ADMIN
-- ==============================
-- Todos os admins agora existem na tabela funcionário
INSERT INTO eta_admin (id_eta, id_funcionario) VALUES
                                                   (1, 1),
                                                   (2, 2),
                                                   (3, 5),
                                                   (4, 6),
                                                   (5, 7);

-- ==============================
-- PRIORIDADE
-- ==============================
INSERT INTO prioridade (nivel) VALUES
                                   ('Baixa'),
                                   ('Média'),
                                   ('Alta'),
                                   ('Urgente');

-- ==============================;
-- UNIDADE_MEDIDA
-- ==============================
INSERT INTO unidade_medida (nome) VALUES
                                      ('kilos'),
                                      ('litro'),
                                      ('unidade');


-- ==============================
-- STATUS
-- ==============================
INSERT INTO status (status) VALUES
                                ('pendente'),
                                ('concluída');

-- ==============================
-- TAREFA
-- ==============================
INSERT INTO tarefa (descricao, id_status, data_criacao, id_prioridade, id_funcionario, data_conclusao) VALUES
                                                                                                           ('Verificar qualidade da água', 1, '2025-09-09', 1, 3, '2025-09-15'),
                                                                                                           ('Atualizar relatório diário', 2, '2025-09-09', 2, 4, '2025-09-19'),
                                                                                                           ('Manutenção dos filtros', 2, '2025-09-09', 3, 5, '2025-09-14'),
                                                                                                           ('Controle de estoque de produtos químicos', 1, '2025-09-09', 4, 2, '2025-09-25'),
                                                                                                           ('Assinar relatórios completos', 2, '2025-09-09', 4, 6, '2025-09-22');

-- ==============================
-- AVISOS
-- ==============================
INSERT INTO avisos (descricao, id_eta, id_prioridade, id_status) VALUES
                                                                     ('Nível de cloro baixo',  1, 2, 1),
                                                                     ('Problema na bomba',  2, 4, 2),
                                                                     ('Revisão de segurança',  3, 3, 2),
                                                                     ('Teste de pH fora do padrão',  4, 4, 1),
                                                                     ('Falha no sistema de monitoramento', 5, 4, 2);

-- ==============================
-- PROCESSO
-- ==============================
INSERT INTO processo (tipo_agua, volume_dagua_inicio, ph_inicial, turbidez_inicial, id_eta, data_processo_inicial) VALUES
                                                                                                                       ('Potável', 1000, 7.2, 5, 1, '2025-08-22'),
                                                                                                                       ('Resíduos', 500, 6.8, 10, 2, '2025-08-22'),
                                                                                                                       ('Potável', 800, 7.0, 3, 3, '2025-08-22'),
                                                                                                                       ('Potável', 600, 7.1, 4, 4, '2025-08-22'),
                                                                                                                       ('Resíduos', 500, 6.5, 8, 5, '2025-08-22'),
                                                                                                                       ('Potável', 800, 7.0, 4, 2, '2025-08-22'),
                                                                                                                       ('Resíduos', 400, 6.9, 6, 3, '2025-08-22');

-- ==============================
-- RELATÓRIO
-- ==============================
INSERT INTO relatorio (volume_dagua_final, ph_final, turbidez_final, id_processo) VALUES
                                                                                      (950, 7.1, 4.5, 1),
                                                                                      (480, 6.9, 9.0,  2),
                                                                                      (790, 7.0, 2.5,  3),
                                                                                      (580, 7.0, 3.5, 4),
                                                                                      (480, 6.6, 7.0,  5),
                                                                                      (780, 7.0, 3.0,  6),
                                                                                      (390, 6.8, 5.5, 7);

-- ==============================
-- PRODUTO
-- ==============================
INSERT INTO produto (nome_produto, tipo, id_unidade) VALUES
                                                         ('Cloro', 'Químico', 1),
                                                         ('Sulfato de Alumínio', 'Químico', 1),
                                                         ('Filtro de Areia', 'Equipamento', 3),
                                                         ('Floculante', 'Químico', 1),
                                                         ('Bomba', 'Equipamento', 3);

-- ==============================
-- ESTOQUE
-- ==============================
INSERT INTO estoque (quantidade, id_produto, id_eta, max_estoque) VALUES
                                                                      (50, 1, 1, 100),
                                                                      (20, 2, 1, 50),
                                                                      (15, 3, 2, 40),
                                                                      (30, 2, 2, 40),
                                                                      (40, 4, 3, 60),
                                                                      (10, 3, 3, 13),
                                                                      (25, 4, 4, 40),
                                                                      (10, 5, 4, 30),
                                                                      (5, 5, 5, 20);

-- ==============================
-- USO_PRODUTO
-- ==============================
INSERT INTO uso_produto (quantidade, id_processo, id_produto) VALUES
                                                                  (5, 1, 1),
                                                                  (2, 2, 2),
                                                                  (1, 3, 3),
                                                                  (3, 4, 4),
                                                                  (1, 5, 5);

-- ==============================
-- PLANO
-- ==============================
INSERT INTO plano (nome, descricao, valor_mensal, duracao_meses) VALUES
                                                                     ('Básico', 'Plano básico com funcionalidades essenciais', 199.90, 6),
                                                                     ('Intermediário', 'Plano intermediário com recursos adicionais', 399.90, 12),
                                                                     ('Premium', 'Plano premium com todos os recursos inclusos', 699.90, 24);

-- ==============================
-- PAGAMENTO
-- ==============================
INSERT INTO pagamento (id_plano, data_pagamento, id_eta_admin) VALUES
                                                                   (1, '2025-09-02', 2),  -- João Santos
                                                                   (2, '2025-09-03', 1),  -- Carlos Silva
                                                                   (3, '2025-09-03', 5);  -- Bruno Dias