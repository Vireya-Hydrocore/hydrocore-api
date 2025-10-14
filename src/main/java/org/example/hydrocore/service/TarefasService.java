package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.TarefasDTO;
import org.example.hydrocore.dto.request.TarefasRequestDTO;
import org.example.hydrocore.dto.response.TarefasResponseDTO;
import org.example.hydrocore.repository.RepositoryFuncionario;
import org.example.hydrocore.repository.RepositoryPrioridade;
import org.example.hydrocore.repository.RepositoryTarefas;
import org.example.hydrocore.repository.entity.Funcionario;
import org.example.hydrocore.repository.entity.Prioridade;
import org.example.hydrocore.repository.entity.Tarefas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefasService {

    @Autowired
    private RepositoryTarefas repositoryTarefas;

    @Autowired
    private RepositoryFuncionario repositoryFuncionario;

    @Autowired
    private RepositoryPrioridade repositoryPrioridade;

    @Autowired
    private ObjectMapper objectMapper;

    public List<TarefasResponseDTO> listarTarefas() {
        List<TarefasDTO> todasTarefas = repositoryTarefas.findAllTarefas();

        if (todasTarefas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma tarefa encontrada.");
        }
        return todasTarefas.stream()
                .map(t -> objectMapper.convertValue(t, TarefasResponseDTO.class))
                .toList();
    }

    public List<TarefasResponseDTO> buscarTarefaPorNome(String nome) {
        List<TarefasDTO> tarefasPorNome = repositoryTarefas.findAllTarefasPorNome(nome);

        if (tarefasPorNome.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma tarefa encontrada para o funcionário com nome: " + nome);
        }
        return tarefasPorNome.stream()
                .map(t -> objectMapper.convertValue(t, TarefasResponseDTO.class))
                .toList();
    }

    @Transactional
    public TarefasResponseDTO criarTarefa(Integer idFuncionario, TarefasRequestDTO requestDTO) {
        Funcionario funcionario = repositoryFuncionario.findById(idFuncionario)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + idFuncionario + " não encontrado."));

        Tarefas novaTarefa = objectMapper.convertValue(requestDTO, Tarefas.class);

        novaTarefa.setDataCriacao(LocalDateTime.now());

        switch (requestDTO.getStatus()) {
            case "concluida":
                novaTarefa.setIdStatus(2);
                break;
            default:
                novaTarefa.setIdStatus(1);
                break;
        }

        if (requestDTO.getNivel() == null || requestDTO.getNivel().isBlank()) {
            throw new IllegalArgumentException("O nível de prioridade (nivel) deve ser fornecido ao criar uma tarefa.");
        }

        Prioridade prioridade = repositoryPrioridade.findByNivel(requestDTO.getNivel());
        novaTarefa.setPrioridade(prioridade);
        novaTarefa.setIdFuncionario(funcionario);

        Tarefas salva = repositoryTarefas.save(novaTarefa);

        return objectMapper.convertValue(salva, TarefasResponseDTO.class);
    }

    @Transactional
    public TarefasResponseDTO atualizarStatusTarefa(Integer idTarefa, String status) {
        Tarefas tarefa = repositoryTarefas.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa com ID " + idTarefa + " não encontrada."));

        if ("pendente".equalsIgnoreCase(status)) {
            tarefa.setIdStatus(1);
            tarefa.setDataConclusao(null);
        } else if ("concluida".equalsIgnoreCase(status)) {
            tarefa.setIdStatus(3);
            tarefa.setDataConclusao(LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("Status inválido. Use 'pendente' ou 'concluida'.");
        }

        Tarefas tarefaAtualizada = repositoryTarefas.save(tarefa);

        return objectMapper.convertValue(tarefaAtualizada, TarefasResponseDTO.class);
    }

    @Transactional
    public TarefasResponseDTO atribuirTarefa(Integer idTarefa, Integer idFuncionario) {
        Tarefas tarefa = repositoryTarefas.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa com ID " + idTarefa + " não encontrada."));

        Funcionario funcionario = repositoryFuncionario.findById(idFuncionario)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + idFuncionario + " não encontrado."));

        tarefa.setIdFuncionario(funcionario);

        Tarefas salva = repositoryTarefas.save(tarefa);

        return objectMapper.convertValue(salva, TarefasResponseDTO.class);
    }

    public TarefasResponseDTO deletarTarefa(Integer idTarefa){
        try {
            Tarefas tarefas = repositoryTarefas.findById(idTarefa)
                    .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

            repositoryTarefas.deletarTarefas(idTarefa);

            return objectMapper.convertValue(tarefas, TarefasResponseDTO.class);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw new EntityNotFoundException("Erro: " + ex.getMessage());
        }
    }

    @Transactional
    public TarefasResponseDTO atualizarParcialmenteTarefa(Integer idTarefa, TarefasRequestDTO requestDTO) {
        Tarefas tarefa = repositoryTarefas.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa com ID " + idTarefa + " não encontrada."));
        try{

            if (requestDTO.getDescricao() != null) {
                tarefa.setDescricao(requestDTO.getDescricao());
            }

            if (requestDTO.getNivel() != null) {
                Prioridade prioridade = repositoryPrioridade.findByNivel(requestDTO.getNivel());
                tarefa.setPrioridade(prioridade);
            }

            Tarefas tarefaAtualizada = repositoryTarefas.save(tarefa);

            return objectMapper.convertValue(tarefaAtualizada, TarefasResponseDTO.class);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw new EntityNotFoundException("Erro: " + ex.getMessage());
        }
    }

    @Transactional
    public TarefasResponseDTO atualizarTarefaCompleta(Integer idTarefa, TarefasRequestDTO requestDTO) {
        Tarefas tarefaExistente = repositoryTarefas.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa com ID " + idTarefa + " não encontrada."));

        if (requestDTO.getDescricao() == null) {
            throw new IllegalArgumentException("A descrição é obrigatória na atualização completa (PUT).");
        }
        tarefaExistente.setDescricao(requestDTO.getDescricao());

        if (requestDTO.getStatus() != null) {
            if ("concluida".equalsIgnoreCase(requestDTO.getStatus())) {
                tarefaExistente.setIdStatus(3);
                tarefaExistente.setDataConclusao(LocalDateTime.now());
            } else if ("pendente".equalsIgnoreCase(requestDTO.getStatus())) {
                tarefaExistente.setIdStatus(1);
                tarefaExistente.setDataConclusao(null);
            } else {
                throw new IllegalArgumentException("Status inválido no PUT. Use 'pendente' ou 'concluida'.");
            }
        } else {
            throw new IllegalArgumentException("O status é obrigatório na atualização completa (PUT).");
        }

        if (requestDTO.getNivel() != null) {
            Prioridade prioridade = repositoryPrioridade.findByNivel(requestDTO.getNivel());
            tarefaExistente.setPrioridade(prioridade);
        } else {
            throw new IllegalArgumentException("O nível de prioridade é obrigatório na atualização completa (PUT).");
        }

        Tarefas salva = repositoryTarefas.save(tarefaExistente);

        return objectMapper.convertValue(salva, TarefasResponseDTO.class);
    }
}