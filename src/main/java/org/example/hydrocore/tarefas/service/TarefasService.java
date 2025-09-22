package org.example.hydrocore.tarefas.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hydrocore.repository.RepositoryFuncionario;
import org.example.hydrocore.repository.RepositoryTarefas;
import org.example.hydrocore.repository.entity.Funcionario;
import org.example.hydrocore.repository.entity.Tarefas;
import org.example.hydrocore.tarefas.dto.TarefasDTO;
import org.example.hydrocore.tarefas.dto.request.TarefasRequestDTO;
import org.example.hydrocore.tarefas.dto.response.TarefasResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TarefasService {

    private RepositoryTarefas repositoryTarefas;
    
    private RepositoryFuncionario repositoryFuncionario;

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

    public Boolean criarTarefa(Long idFuncionario, TarefasRequestDTO requestDTO) {
        Optional<Funcionario> idFunc = repositoryFuncionario.findById(idFuncionario.intValue());

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

//    public TarefasResponseDTO atualizarStatusTarefa(Long idTarefa, TarefasRequestDTO requestDTO) {
//        Optional<Tarefas> idTarefaDB = repositoryTarefas.findById(idTarefa.intValue());
//
//        if (!idTarefaDB.isPresent()) {
//            throw new IllegalArgumentException("Tarefa não encontrada");
//        }
//
//        TarefasDTO save = new TarefasDTO();
//        save.setIdTarefa(idTarefaDB.get().getIdTarefa());
//        save.setDataCriacao(idTarefaDB.get().getDataCriacao());
//        save.setDataConclusao(idTarefaDB.get().getDataConclusao());
//        save.setStatus(requestDTO.getStatus());
//        save.setDescricao(requestDTO.getDescricao());
//        save.setNivel(requestDTO.getNivel());
//        save.setIdFuncionario(requestDTO.getIdFuncionario());
//
//        repositoryTarefas.save(save);
//
//        return objectMapper.convertValue(save, TarefasResponseDTO.class);
//    }
}

