package org.example.hydrocore.repository;

import org.example.hydrocore.dto.EstoqueDTO;
import org.example.hydrocore.repository.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryEstoque extends JpaRepository<Estoque, Integer> {

    @Query("SELECT new org.example.hydrocore.dto.EstoqueDTO(" +
            "e.idEstoque, e.quantidade, p.nomeProduto, eta.nome) " +
            "FROM estoque e " +
            "JOIN e.produto p " +
            "JOIN e.eta eta")
    List<EstoqueDTO> findAllEstoqueComNomes();

}
