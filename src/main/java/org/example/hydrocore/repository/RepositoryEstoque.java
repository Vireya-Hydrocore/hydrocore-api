package org.example.hydrocore.repository;

import org.example.hydrocore.dto.EstoqueDTO;
import org.example.hydrocore.projection.EstoqueInfoProjection;
import org.example.hydrocore.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryEstoque extends JpaRepository<Estoque, Integer> {

    @Query(value = """
    SELECT
        e.id_estoque AS id,
        e.quantidade AS quantidade,
        p.nome_produto AS nomeProduto,
        eta.nome AS nomeEta
    FROM estoque e
    JOIN produto p ON p.id_produto = e.id_produto
    JOIN eta ON eta.id_eta = e.id_eta
    WHERE (:nomeEta IS NULL OR LOWER(eta.nome::text) LIKE LOWER(CONCAT('%', :nomeEta, '%')))
    """, nativeQuery = true)
    List<EstoqueInfoProjection> findAllEstoqueComNomes(@Param("nomeEta") String nomeEta);

    @Query("SELECT new org.example.hydrocore.dto.EstoqueDTO(" +
            "e.idEstoque, e.quantidade, p.nome, eta.nome) " +
            "FROM estoque e " +
            "JOIN e.produto p " +
            "JOIN e.eta eta " +
            "WHERE p.idProduto = :idProduto")
    List<EstoqueDTO> findAllEstoquePorProduto(@Param("idProduto") Integer idProduto);

    @Query(value = """
    SELECT
        e.id_eta AS id_eta,
        STRING_AGG(p.nome_produto, ', ' ORDER BY p.nome_produto) AS produtos
    FROM estoque es
    JOIN eta e ON e.id_eta = es.id_eta
    JOIN produto p ON p.id_produto = es.id_produto
    WHERE (:nomeEta IS NULL OR LOWER(e.nome) LIKE LOWER(CONCAT('%', :nomeEta, '%'))) -- Adiciona a condição opcional
    GROUP BY e.id_eta
    ORDER BY e.id_eta
    """, nativeQuery = true)
    List<Object[]> buscarProdutosPorEta(@Param("nomeEta") String nomeEta);

    @Query(value = "SELECT adicionar_estoque(:idProduto, :idEta, :quantidade)", nativeQuery = true)
    Integer adicionarEstoque(@Param("idProduto") Integer idProduto, @Param("idEta") Integer idEta, @Param("quantidade") java.math.BigDecimal quantidade);

    @Query(value = "SELECT tirar_estoque(:idProduto, :idEta, :quantidade)", nativeQuery = true)
    Integer tirarEstoque(@Param("idProduto") Integer idProduto, @Param("idEta") Integer idEta, @Param("quantidade") java.math.BigDecimal quantidade);
}
