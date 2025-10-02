package org.example.hydrocore.repository;

import org.example.hydrocore.projection.FuncionarioDTO;
import org.example.hydrocore.repository.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryFuncionario extends JpaRepository<Funcionario, Integer> {

    @Query(value = "SELECT * FROM relatorio_funcionarios(:idFuncionario)", nativeQuery = true)
    List<FuncionarioDTO> listarFuncionarios(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "DELETE FROM funcionario WHERE id_funcionario = :idFuncionario RETURNING *", nativeQuery = true)
    Funcionario deletarFuncionario(Integer idFuncionario);

    @Query(value = "SELECT id_funcionario FROM funcionario WHERE email = :email", nativeQuery = true)
    Integer getIdFuncionario(String email);

}
