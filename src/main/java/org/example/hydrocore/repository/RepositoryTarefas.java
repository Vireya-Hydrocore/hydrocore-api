package org.example.hydrocore.repository;

import org.example.hydrocore.repository.entity.Tarefas;
import org.example.hydrocore.dto.TarefasDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryTarefas extends JpaRepository<Tarefas, Integer> {

    @Query("""
       SELECT new org.example.hydrocore.dto.TarefasDTO(
            t.idTarefa,
            t.descricao,
            t.dataCriacao,
            t.dataConclusao,
            t.status,
            p.nivel,
            f.idFuncionario
       )
       FROM tarefa t
       LEFT JOIN t.prioridade p
       LEFT JOIN t.idFuncionario f
       """)
    List<TarefasDTO> findAllTarefas();

    @Query("""
       SELECT new org.example.hydrocore.dto.TarefasDTO(
            t.idTarefa,
            t.descricao,
            t.dataCriacao,
            t.dataConclusao,
            t.status,
            p.nivel,
            f.idFuncionario
       )
       FROM tarefa t
       JOIN t.idFuncionario f
       JOIN t.prioridade p
       WHERE f.nome LIKE CONCAT('%', :nome, '%')
       """)
    List<TarefasDTO> findAllTarefasPorNome(@Param("nome") String nome);

}
