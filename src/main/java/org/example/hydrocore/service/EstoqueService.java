package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hydrocore.dto.EstoqueDTO;
import org.example.hydrocore.dto.response.EstoqueResponseDTO;
import org.example.hydrocore.dto.response.ProdutoEtaResponseDTO;
import org.example.hydrocore.projection.EstoqueInfoProjection;
import org.example.hydrocore.repository.RepositoryEstoque;
import org.example.hydrocore.repository.RepositoryEta;
import org.example.hydrocore.repository.RepositoryProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private RepositoryEstoque repositoryEstoque;

    @Autowired
    private RepositoryProduto repositoryProduto;

    @Autowired
    private RepositoryEta repositoryEta;

    @Autowired
    private ObjectMapper objectMapper;

    public List<EstoqueResponseDTO> mostrarEstoqueComNome(String nome) {
        List<EstoqueInfoProjection> stock = repositoryEstoque.findAllEstoqueComNomes(nome);

        if (stock.isEmpty()) {
            return Collections.emptyList();
        }

        return stock.stream()
                .map(estoqueDTO -> {
                    EstoqueResponseDTO response = objectMapper.convertValue(estoqueDTO, EstoqueResponseDTO.class);

                    preencherStatus(response);

                    return response;
                })
                .toList();
    }

    public List<ProdutoEtaResponseDTO> mostrarTotalProdutosPorEta(String nome) {
        List<Object[]> rows = repositoryEstoque.buscarProdutosPorEta(nome);

        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }

        return rows.stream().map(row -> {
            Integer id = row[0] == null ? null : ((Number) row[0]).intValue();
            String produtosConcatenados = row.length > 1 && row[1] != null ? row[1].toString() : "";

            List<String> produtosList = produtosConcatenados.isBlank()
                    ? Collections.emptyList()
                    : Arrays.stream(produtosConcatenados.split(",\\s*"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();

            return new ProdutoEtaResponseDTO(id, produtosList);
        }).toList();
    }

    public EstoqueResponseDTO adicionarProdutoEstoque(Integer idProduto, Integer idEta, BigDecimal quantidade) {
        List<EstoqueDTO> estoquesAnteriores = repositoryEstoque.findAllEstoquePorProduto(idProduto);

        EstoqueDTO estoqueAnteriorDTO = estoquesAnteriores.stream()
                .filter(e -> e.getId().equals(idEta))
                .findFirst()
                .orElse(null);

        Integer quantidadeAnterior = estoqueAnteriorDTO != null ? estoqueAnteriorDTO.getQuantidade() : 0;

        repositoryEstoque.adicionarEstoque(idProduto, idEta, quantidade);

        List<EstoqueDTO> estoquesAtuais = repositoryEstoque.findAllEstoquePorProduto(idProduto);

        EstoqueDTO estoqueAtualDTO = estoquesAtuais.stream()
                .filter(e -> e.getId().equals(idEta))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Estoque deveria existir após a adição e não foi encontrado para o Produto/Eta."));

        Integer quantidadeAtual = estoqueAtualDTO.getQuantidade();

        if (quantidadeAtual > quantidadeAnterior) {
            EstoqueDTO estoqueParaRetorno = new EstoqueDTO(
                    estoqueAtualDTO.getId(),
                    quantidadeAtual,
                    estoqueAtualDTO.getNomeProduto(),
                    estoqueAtualDTO.getNomeEta()
            );

            EstoqueResponseDTO estoqueResponseDTO = objectMapper.convertValue(estoqueParaRetorno, EstoqueResponseDTO.class);

            preencherStatus(estoqueResponseDTO);

            return estoqueResponseDTO;
        } else {
            throw new IllegalStateException("A quantidade de estoque atual (" + quantidadeAtual + ") não é maior que a anterior (" + quantidadeAnterior + "). A operação de adição pode ter falhado ou não alterou o estoque.");
        }
    }

    public EstoqueResponseDTO removerProdutoEstoque(Integer idProduto, Integer idEta, BigDecimal quantidade) {

        List<EstoqueDTO> estoquesAnteriores = repositoryEstoque.findAllEstoquePorProduto(idProduto);

        EstoqueDTO estoqueAnteriorDTO = estoquesAnteriores.stream()
                .filter(e -> e.getId().equals(idEta))
                .findFirst()
                .orElse(null);

        Integer quantidadeAnterior = estoqueAnteriorDTO != null ? estoqueAnteriorDTO.getQuantidade() : 0;

        if (quantidadeAnterior == 0) {
            throw new IllegalArgumentException("Não há estoque do produto (ID: " + idProduto + ") na ETA (ID: " + idEta + ") para ser removido.");
        }

        repositoryEstoque.tirarEstoque(idProduto, idEta, quantidade);

        List<EstoqueDTO> estoquesAtuais = repositoryEstoque.findAllEstoquePorProduto(idProduto);

        EstoqueDTO estoqueAtualDTO = estoquesAtuais.stream()
                .filter(e -> e.getId().equals(idEta))
                .findFirst()
                .orElse(null);

        Integer quantidadeAtual = estoqueAtualDTO != null ? estoqueAtualDTO.getQuantidade() : 0;

        if (quantidadeAtual < quantidadeAnterior) {

            EstoqueDTO baseDTO = estoqueAtualDTO != null ? estoqueAtualDTO : estoqueAnteriorDTO;

            EstoqueDTO estoqueParaRetorno = new EstoqueDTO(
                    idEta,
                    quantidadeAtual,
                    baseDTO.getNomeProduto(),
                    baseDTO.getNomeEta()
            );

            EstoqueResponseDTO estoqueResponseDTO = objectMapper.convertValue(estoqueParaRetorno, EstoqueResponseDTO.class);

            preencherStatus(estoqueResponseDTO);

            return estoqueResponseDTO;
        } else {
            throw new IllegalStateException("A quantidade de estoque atual (" + quantidadeAtual + ") não é menor que a anterior (" + quantidadeAnterior + "). A operação de remoção pode ter falhado ou a quantidade solicitada era inválida.");
        }
    }

    public void preencherStatus(EstoqueResponseDTO response){
        if (response.getQuantidade() == 0) {
            response.setStatus("Insuficiente");
        } else if (response.getQuantidade() <= 15) {
            response.setStatus("Próximo ao fim");
        } else {
            response.setStatus("Suficiente");
        }
    }

}
