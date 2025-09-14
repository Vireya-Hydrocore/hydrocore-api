package org.example.hydrocore.repository;

import org.example.hydrocore.repository.entity.Tarefas;
import org.example.hydrocore.tarefas.dto.TarefasDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryTarefas extends JpaRepository<Tarefas, Integer> {

    @Query("SELECT new org.example.hydrocore.tarefas.dto.TarefasDTO(" +
            "t.idTarefa, t.descricao, t.dataCriacao, t.dataConclusao, t.status, p.nivel, t.idFuncionario ) " +
            "FROM tarefa t JOIN prioridade p")
    List<TarefasDTO> findAllTarefas();

}
