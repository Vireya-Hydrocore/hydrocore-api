package org.example.hydrocore.repository;

import org.example.hydrocore.dto.TarefasProjection;
import org.example.hydrocore.model.Tarefas;
import org.example.hydrocore.projection.ResumoTarefasEtaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryTarefas extends JpaRepository<Tarefas, Integer> {

    @Query(value = """
        SELECT t.id_tarefa AS id, t.descricao, t.data_criacao AS dataCriacao, t.data_conclusao AS dataConclusao, p.nivel AS prioridade, f.nome AS nome, s.status AS status FROM tarefa t
       JOIN funcionario f ON t.id_funcionario = f.id_funcionario
       JOIN cargo c ON f.id_cargo = c.id_cargo
       JOIN prioridade p ON t.id_prioridade = p.id_prioridade
       JOIN status s ON t.id_status = s.id_status
       """, nativeQuery = true)
    List<TarefasProjection> findAllTarefas();

    @Query(value = """
   SELECT t.id_tarefa AS id, t.descricao, t.data_criacao AS dataCriacao, t.data_conclusao AS dataConclusao, p.nivel AS prioridade, f.nome AS nome, s.status AS status FROM tarefa t
   JOIN funcionario f ON t.id_funcionario = f.id_funcionario
   JOIN cargo c ON f.id_cargo = c.id_cargo
   JOIN prioridade p ON t.id_prioridade = p.id_prioridade
   JOIN status s ON t.id_status = s.id_status
   WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
   """, nativeQuery = true)
    List<TarefasProjection> findAllTarefasPorNome(@Param("nome") String nome);

    @Query(value = """
       SELECT t.id_tarefa AS id, t.descricao, t.data_criacao AS dataCriacao, t.data_conclusao AS dataConclusao, p.nivel AS prioridade, f.nome AS nome, s.status AS status FROM tarefa t
       JOIN funcionario f ON t.id_funcionario = f.id_funcionario
       JOIN cargo c ON f.id_cargo = c.id_cargo
       JOIN prioridade p ON t.id_prioridade = p.id_prioridade
       JOIN status s ON t.id_status = s.id_status
       WHERE t.id_tarefa = :idTarefa
       """, nativeQuery = true)
    TarefasProjection findTarefasPorId(@Param("idTarefa") Integer idTarefa);

    @Query(value = "SELECT * FROM resumo_tarefas_eta(:idEta)", nativeQuery = true)
    ResumoTarefasEtaProjection resumoTarefasEta(@Param("idEta") Integer idEta);

}