package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.response.UnidadeMedidaResponseDTO;
import org.example.hydrocore.model.UnidadeMedida;
import org.example.hydrocore.repository.RepositoryUnidadeMedida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeMedidaService {

    @Autowired
    private RepositoryUnidadeMedida repositoryUnidadeMedida;

    @Autowired
    private ObjectMapper objectMapper;

    public List<UnidadeMedidaResponseDTO> mostrarUnidadeMedida() {

        List<UnidadeMedida> unidadeAll = repositoryUnidadeMedida.findAll();

        if (unidadeAll.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma unidade de medida encontrada.");
        }

        return unidadeAll.stream().map(u ->
                objectMapper.convertValue(u, UnidadeMedidaResponseDTO.class)
                ).toList();

    }


}
