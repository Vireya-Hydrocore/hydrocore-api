package org.example.hydrocore.repository;

import org.example.hydrocore.dto.ProdutoDTO;
import org.example.hydrocore.dto.ProdutosUsadosMesDTO;
import org.example.hydrocore.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryProduto extends JpaRepository<Produto, Integer> {

    @Query(value = "DELETE FROM produto WHERE id_produto = :idProduto RETURNING *", nativeQuery = true)
    Produto deletarProduto(Integer idProduto);

    @Query(value = "SELECT id_produto, nome_produto, tipo, u.nome FROM produto JOIN unidade_medida u ON produto.id_unidade = u.id_unidade", nativeQuery = true)
    List<ProdutoDTO> listarProdutos();

    @Query(value = "SELECT * FROM produtos_usados_mes(:mes, :ano)", nativeQuery = true)
    List<ProdutosUsadosMesDTO> listarProdutosUsadosMes(@Param("mes") Integer mes, @Param("ano") Integer ano);

}
