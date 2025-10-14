package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hydrocore.dto.EstoqueDTO;
import org.example.hydrocore.dto.ProdutoEtaDTO;
import org.example.hydrocore.dto.response.EstoqueResponseDTO;
import org.example.hydrocore.dto.response.ProdutoEtaResponseDTO;
import org.example.hydrocore.repository.RepositoryEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private RepositoryEstoque repositoryEstoque;

    @Autowired
    private ObjectMapper objectMapper;

    public List<EstoqueResponseDTO> mostrarEstoqueComNome() {
        List<EstoqueDTO> stock = repositoryEstoque.findAllEstoqueComNomes();

        if (stock.isEmpty()) {
            return Collections.emptyList();
        }

        return stock.stream()
                .map(estoqueDTO -> {
                    EstoqueResponseDTO response = objectMapper.convertValue(estoqueDTO, EstoqueResponseDTO.class);

                    if (response.getQuantidade() == 0) {
                        response.setStatus("Insuficiente");
                    } else if (response.getQuantidade() <= 15) {
                        response.setStatus("Próximo ao fim");
                    } else {
                        response.setStatus("Suficiente");
                    }

                    return response;
                })
                .toList();
    }

    public List<ProdutoEtaResponseDTO> mostrarTotalProdutosPorEta() {
        List<Object[]> rows = repositoryEstoque.buscarProdutosPorEta();

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


}
