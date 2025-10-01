package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hydrocore.dto.TarefasDTO;
import org.example.hydrocore.dto.request.TarefasRequestDTO;
import org.example.hydrocore.dto.response.TarefasResponseDTO;
import org.example.hydrocore.repository.RepositoryFuncionario;
import org.example.hydrocore.repository.RepositoryTarefas;
import org.example.hydrocore.repository.entity.Funcionario;
import org.example.hydrocore.repository.entity.Tarefas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TarefasService {

    @Autowired
    private RepositoryTarefas repositoryTarefas;

    @Autowired
    private RepositoryFuncionario repositoryFuncionario;

    @Autowired
    private ObjectMapper objectMapper;

    public List<TarefasResponseDTO> listarTarefas() {
        List<Tarefas> allTarefas = repositoryTarefas.findAllTarefas();

        if (allTarefas.isEmpty()) {
            return Collections.emptyList();
        }

        return allTarefas.stream()
                .map(t -> objectMapper.convertValue(t, TarefasResponseDTO.class))
                .toList();
    }

    public List<TarefasResponseDTO> buscarTarefaPorNome(String nome) {
        List<Tarefas> allTarefasPorNome = repositoryTarefas.findAllTarefasPorNome(nome);

        if (allTarefasPorNome.isEmpty()) {
            return Collections.emptyList();
        }

        return allTarefasPorNome.stream()
                .map(t -> objectMapper.convertValue(t, TarefasResponseDTO.class))
                .toList();
    }

    public Boolean criarTarefa(Integer idFuncionario, TarefasRequestDTO requestDTO) {
        Optional<Funcionario> idFunc = repositoryFuncionario.findById(idFuncionario);

        if (!idFunc.isPresent()) {
            throw new IllegalArgumentException("Funcionário não encontrado");
        }

        TarefasDTO tarefasDTO = new TarefasDTO();
        tarefasDTO.setDescricao(requestDTO.getDescricao());
        tarefasDTO.setDataCriacao(LocalDateTime.now());
        tarefasDTO.setStatus(requestDTO.getStatus());
        tarefasDTO.setNivel(requestDTO.getNivel());
        tarefasDTO.setIdFuncionario(idFunc.get().getIdFuncionario());

        Tarefas save = repositoryTarefas.save(objectMapper.convertValue(tarefasDTO, Tarefas.class));

        if (save != null) {
            return true;
        }

         return false;

    }

    public TarefasResponseDTO atualizarStatusTarefa(Long idTarefa, String status) {
        Tarefas tarefa = repositoryTarefas.findById(idTarefa.intValue())
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        if ("pendente".equalsIgnoreCase(status)) {
            tarefa.setStatus("pendente");
            tarefa.setDataConclusao(null);
        } else if ("concluida".equalsIgnoreCase(status)) {
            tarefa.setStatus("concluida");
            tarefa.setDataConclusao(LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("Status inválido");
        }

        Tarefas tarefaAtualizada = repositoryTarefas.save(tarefa);

        return objectMapper.convertValue(tarefaAtualizada, TarefasResponseDTO.class);

    }

    public TarefasResponseDTO atribuirTarefa(Integer idTarefa, Integer idFuncionario) {
        Tarefas tarefa = repositoryTarefas.findById(idTarefa)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        Optional<Funcionario> idFunc = repositoryFuncionario.findById(idFuncionario);

        if (!idFunc.isPresent()) {
            throw new IllegalArgumentException("Funcionário não encontrado");
        }

        tarefa.setIdFuncionario(idFunc.get());
        Tarefas save = repositoryTarefas.save(tarefa);

        return objectMapper.convertValue(save, TarefasResponseDTO.class);
    }
}

