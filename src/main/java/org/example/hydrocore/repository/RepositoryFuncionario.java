package org.example.hydrocore.repository;

import org.example.hydrocore.dto.FuncionarioEmailDTO;
import org.example.hydrocore.dto.ResumoTarefasDTO;
import org.example.hydrocore.projection.FuncionarioDTO;
import org.example.hydrocore.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryFuncionario extends JpaRepository<Funcionario, Integer> {

    @Query(value = "SELECT * FROM relatorio_funcionarios(:idFuncionario)", nativeQuery = true)
    List<FuncionarioDTO> listarFuncionarios(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "DELETE FROM funcionario WHERE id_funcionario = :idFuncionario RETURNING *", nativeQuery = true)
    Funcionario deletarFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "SELECT f.nome AS nome, c.nome AS cargo, f.id_funcionario AS id FROM funcionario f JOIN cargo c ON f.id_cargo = c.id_cargo WHERE f.email = :email", nativeQuery = true)
    FuncionarioEmailDTO getFuncionarioByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM resumo_tarefas_usuario(:idFuncionario)", nativeQuery = true)
    ResumoTarefasDTO resumoTarefasUsuario(@Param("idFuncionario") Integer idFuncionario);

}
