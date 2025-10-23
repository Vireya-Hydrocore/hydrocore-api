package org.example.hydrocore.repository;

import org.example.hydrocore.projection.ProdutoProjection;
import org.example.hydrocore.projection.ProdutosUsadosMesProjection;
import org.example.hydrocore.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryProduto extends JpaRepository<Produto, Integer> {

    @Query(value = "DELETE FROM produto WHERE id_produto = :idProduto RETURNING *", nativeQuery = true)
    Produto deletarProduto(Integer idProduto);

    @Query(value = """ 
            SELECT p.id_produto AS id,
            p.nome_produto AS nomeProduto,
            p.tipo AS tipo,
            u.nome AS unidade,
            u.id_unidade AS idUnidade
            FROM produto p 
            JOIN unidade_medida u ON p.id_unidade = u.id_unidade
            WHERE (:idProduto IS NULL OR p.id_produto = :idProduto);
            """, nativeQuery = true)
    List<ProdutoProjection> listarProdutos(@Param("idProduto")  Integer idProduto);

    @Query(value = "SELECT * FROM produtos_usados_mes(:mes, :ano)", nativeQuery = true)
    List<ProdutosUsadosMesProjection> listarProdutosUsadosMes(@Param("mes") Integer mes, @Param("ano") Integer ano);

}
