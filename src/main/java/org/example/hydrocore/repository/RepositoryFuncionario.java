package org.example.hydrocore.repository;

import org.example.hydrocore.repository.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryFuncionario extends JpaRepository<Funcionario, Integer> {

    @Query(value = "SELECT * FROM relatorio_funcionarios()", nativeQuery = true)
    List<Funcionario> listarFuncionarios();

    @Query(value = "DELETE FROM funcionarios WHERE id = :idFuncionario RETURNING *", nativeQuery = true)
    Funcionario deletarFuncionario(Integer idFuncionario);

}
