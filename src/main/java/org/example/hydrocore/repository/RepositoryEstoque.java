package org.example.hydrocore.repository;

import org.example.hydrocore.dto.EstoqueDTO;
import org.example.hydrocore.dto.ProdutoEtaDTO;
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

    @Query(value = """
        SELECT
            e.id_eta AS id_eta,
            STRING_AGG(p.nome_produto, ', ' ORDER BY p.nome_produto) AS produtos
        FROM estoque es
        JOIN eta e ON e.id_eta = es.id_eta
        JOIN produto p ON p.id_produto = es.id_produto
        GROUP BY e.id_eta
        ORDER BY e.id_eta
        """, nativeQuery = true)
        List<Object[]> buscarProdutosPorEta();

}
