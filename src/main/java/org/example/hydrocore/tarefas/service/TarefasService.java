package org.example.hydrocore.tarefas.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hydrocore.repository.RepositoryTarefas;
import org.example.hydrocore.repository.entity.Tarefas;
import org.example.hydrocore.tarefas.dto.TarefasDTO;
import org.example.hydrocore.tarefas.dto.response.TarefasResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TarefasService {

    @Autowired
    private RepositoryTarefas repositoryTarefas;

    @Autowired
    private ObjectMapper objectMapper;

    public List<TarefasResponseDTO> listarTarefas() {
        List<TarefasDTO> allTarefas = repositoryTarefas.findAllTarefas();

        if (allTarefas.isEmpty()) {
            return Collections.emptyList();
        }

        return allTarefas.stream()
                .map(t -> objectMapper.convertValue(t, TarefasResponseDTO.class))
                .toList();
    }

    public List<TarefasResponseDTO> buscarTarefaPorNome(String nome) {
        List<TarefasDTO> allTarefasPorNome = repositoryTarefas.findAllTarefasPorNome(nome);

        if (allTarefasPorNome.isEmpty()) {
            return Collections.emptyList();
        }

        return allTarefasPorNome.stream()
                .map(t -> objectMapper.convertValue(t, TarefasResponseDTO.class))
                .toList();
    }


}
