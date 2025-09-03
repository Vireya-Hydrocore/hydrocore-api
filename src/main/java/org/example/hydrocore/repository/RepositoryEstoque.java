package org.example.hydrocore.repository;

import org.example.hydrocore.estoque.dto.EstoqueDTO;
import org.example.hydrocore.repository.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryEstoque extends JpaRepository<Estoque,Integer> {

    @Query("SELECT e.idEstoque, e.quantidade, p.nomeProduto, et.nome " +
            "FROM estoque e " +
            "JOIN produto p ON e.idProduto = p.idProduto " +
            "JOIN eta et ON e.idEta = et.idEta")
    List<EstoqueDTO> findAllEstoqueComNomes();

}
