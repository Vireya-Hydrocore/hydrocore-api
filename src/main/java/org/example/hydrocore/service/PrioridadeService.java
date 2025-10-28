package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.response.PrioridadeResponseDTO;
import org.example.hydrocore.model.Prioridade;
import org.example.hydrocore.repository.RepositoryPrioridade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrioridadeService {

    @Autowired
    private RepositoryPrioridade repositoryPrioridade;

    @Autowired
    private ObjectMapper objectMapper;

    public List<PrioridadeResponseDTO> mostrarPrioridade() {

        List<Prioridade> unidadeAll = repositoryPrioridade.findAll();

        if (unidadeAll.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma prioridade encontrada.");
        }

        return unidadeAll.stream().map(u ->
                objectMapper.convertValue(u, PrioridadeResponseDTO.class)
                ).toList();
    }
}
