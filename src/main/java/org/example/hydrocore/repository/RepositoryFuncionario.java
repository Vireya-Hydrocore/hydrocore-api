package org.example.hydrocore.repository;

import org.example.hydrocore.dto.FuncionarioFunctionDTO;
import org.example.hydrocore.repository.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryFuncionario extends JpaRepository<Funcionario, Long> {

    @Query(value = "SELECT * FROM relatorio_funcionarios()", nativeQuery = true)
    List<FuncionarioFunctionDTO> listarFuncionarios();

}
