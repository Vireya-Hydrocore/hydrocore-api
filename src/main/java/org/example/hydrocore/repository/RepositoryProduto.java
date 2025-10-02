package org.example.hydrocore.repository;

import org.example.hydrocore.repository.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositoryProduto extends JpaRepository<Produto, Integer> {

    @Query(value = "DELETE FROM produto WHERE id_produto = :idProduto RETURNING *", nativeQuery = true)
    Produto deletarProduto(Integer idProduto);

}
