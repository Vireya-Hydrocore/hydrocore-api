package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hydrocore.dto.EstoqueDTO;
import org.example.hydrocore.dto.response.EstoqueResponseDTO;
import org.example.hydrocore.repository.RepositoryEstoque;
import org.example.hydrocore.repository.entity.Estoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
