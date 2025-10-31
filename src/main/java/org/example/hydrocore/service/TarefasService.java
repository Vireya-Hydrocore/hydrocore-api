package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.response.ResumoTarefasEtaResponseDTO;
import org.example.hydrocore.projection.TarefasProjection;
import org.example.hydrocore.dto.request.TarefasCreateRequestDTO;
import org.example.hydrocore.dto.request.TarefasRequestDTO;
import org.example.hydrocore.dto.response.TarefasResponseDTO;
import org.example.hydrocore.model.Funcionario;
import org.example.hydrocore.model.Prioridade;
import org.example.hydrocore.model.Status;
import org.example.hydrocore.model.Tarefas;
import org.example.hydrocore.projection.ResumoTarefasEtaProjection;
import org.example.hydrocore.repository.RepositoryFuncionario;
import org.example.hydrocore.repository.RepositoryPrioridade;
import org.example.hydrocore.repository.RepositoryStatus;
import org.example.hydrocore.repository.RepositoryTarefas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TarefasService {

    @Autowired
    private RepositoryTarefas repositoryTarefas;

    @Autowired
    private RepositoryFuncionario repositoryFuncionario;

    @Autowired
    private RepositoryPrioridade repositoryPrioridade;

    @Autowired
    private RepositoryStatus repositoryStatus;

    @Autowired
    private ObjectMapper objectMapper;

    public List<TarefasResponseDTO> listarTarefas(Boolean tarefasPendentes) {
        List<TarefasProjection> todasTarefas = repositoryTarefas.findAllTarefas(tarefasPendentes);

        if (todasTarefas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma tarefa encontrada.");
        }

        return todasTarefas.stream()
                .map(tarefa -> objectMapper.convertValue(tarefa, TarefasResponseDTO.class))
                .toList();

    }

    public List<TarefasResponseDTO> buscarTarefaPorNome(String nome, Boolean tarefasConcluidas) {
        List<TarefasProjection> tarefasPorNome = repositoryTarefas.findAllTarefasPorNome(nome, tarefasConcluidas);

        if (tarefasPorNome.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma tarefa encontrada para o funcionário com nome: " + nome);
        }

        if (tarefasConcluidas) {
            return tarefasPorNome.stream()
                    .map(t -> {
                        TarefasResponseDTO dto = new TarefasResponseDTO();
                        dto.setId(t.getId());
                        dto.setDescricao(t.getDescricao());
                        dto.setDataCriacao(t.getDataCriacao());
                        dto.setDataConclusao(t.getDataConclusao());
                        dto.setPrioridade(t.getPrioridade());
                        dto.setNome(t.getNome());
                        dto.setStatus(t.getStatus());
                        return dto;
                    })
                    .toList();
        }

        return tarefasPorNome.stream()
                .filter(t -> !"concluída".equalsIgnoreCase(t.getStatus()))
                .map(t -> {
                    TarefasResponseDTO dto = new TarefasResponseDTO();
                    dto.setId(t.getId());
                    dto.setDescricao(t.getDescricao());
                    dto.setDataCriacao(t.getDataCriacao());
                    dto.setDataConclusao(t.getDataConclusao());
                    dto.setPrioridade(t.getPrioridade());
                    dto.setNome(t.getNome());
                    dto.setStatus(t.getStatus());
                    return dto;
                })
                .toList();
    }


    @Transactional
    public TarefasResponseDTO criarTarefa(Integer idFuncionario, TarefasCreateRequestDTO requestDTO) {
        Funcionario funcionario = repositoryFuncionario.findById(idFuncionario)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + idFuncionario + " não encontrado."));

        if (requestDTO.getPrioridade() == null || requestDTO.getPrioridade().isBlank()) {
            throw new IllegalArgumentException("O nível de prioridade deve ser fornecido ao criar uma tarefa.");
        }

        Prioridade prioridade = repositoryPrioridade.findByNivelCaseInsensitive(requestDTO.getPrioridade());

        if (prioridade == null) {
            throw new EntityNotFoundException("Nível de prioridade '" + requestDTO.getPrioridade() + "' não encontrado. Verifique se o nível existe no banco.");
        }

        Tarefas novaTarefa = new Tarefas();

        novaTarefa.setDescricao(requestDTO.getDescricao());
        novaTarefa.setPrioridade(prioridade);
        novaTarefa.setIdFuncionario(funcionario);
        novaTarefa.setDataCriacao(LocalDate.now());
        novaTarefa.setIdStatus(1);

        Tarefas salva = repositoryTarefas.save(novaTarefa);

        Optional<Status> statusOptional = repositoryStatus.findById(salva.getIdStatus());

        TarefasResponseDTO dto = new TarefasResponseDTO();

        dto.setId(salva.getIdTarefa());
        dto.setDescricao(salva.getDescricao());
        dto.setDataCriacao(salva.getDataCriacao());
        dto.setDataConclusao(salva.getDataConclusao());

        dto.setPrioridade(salva.getPrioridade() != null ? salva.getPrioridade().getNivel() : null);
        dto.setNome(salva.getIdFuncionario() != null ? salva.getIdFuncionario().getNome() : null);

        if (statusOptional.isPresent()) {
            dto.setStatus(statusOptional.get().getStatus());
        } else {
            dto.setStatus("Status Desconhecido");
        }

        return dto;
    }

    @Transactional
    public TarefasResponseDTO atualizarStatusTarefa(Integer idTarefa, String status) {
        Tarefas tarefa = repositoryTarefas.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa com ID " + idTarefa + " não encontrada."));

        if ("pendente".equalsIgnoreCase(status)) {
            tarefa.setIdStatus(1);
            tarefa.setDataConclusao(null);
        } else if ("concluida".equalsIgnoreCase(status)) {
            tarefa.setIdStatus(2);
            tarefa.setDataConclusao(LocalDate.now());
        } else {
            throw new IllegalArgumentException("Status inválido. Use 'pendente' ou 'concluida'.");
        }

        repositoryTarefas.save(tarefa);
        TarefasProjection tarefasPorId = repositoryTarefas.findTarefasPorId(idTarefa);

        return objectMapper.convertValue(tarefasPorId, TarefasResponseDTO.class);
    }

    @Transactional
    public TarefasResponseDTO atribuirTarefa(Integer idTarefa, Integer idFuncionario) {
        Tarefas tarefa = repositoryTarefas.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa com ID " + idTarefa + " não encontrada."));

        Funcionario funcionario = repositoryFuncionario.findById(idFuncionario)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + idFuncionario + " não encontrado."));

        tarefa.setIdFuncionario(funcionario);

        repositoryTarefas.save(tarefa);
        TarefasProjection tarefasPorId = repositoryTarefas.findTarefasPorId(idTarefa);

        return objectMapper.convertValue(tarefasPorId, TarefasResponseDTO.class);
    }

    @Transactional
    public TarefasResponseDTO deletarTarefa(Integer idTarefa){
        Tarefas tarefas = repositoryTarefas.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa com ID " + idTarefa + " não encontrada."));


        Optional<Funcionario> funcionario = repositoryFuncionario.findById(tarefas.getIdFuncionario().getIdFuncionario());

        if (funcionario.isEmpty()) {
            throw new EntityNotFoundException("Não encontrado funcionario com esse id");
        }

        Optional<Status> status = repositoryStatus.findById(tarefas.getIdStatus());

        if (status.isEmpty()) {
            throw new EntityNotFoundException("Não encontrado status com esse id");
        }

        repositoryTarefas.deleteById(idTarefa);
        TarefasResponseDTO tarefasResponseDTO = new TarefasResponseDTO();

        tarefasResponseDTO.setId(idTarefa);
        tarefasResponseDTO.setDataConclusao(tarefas.getDataConclusao());
        tarefasResponseDTO.setNome(funcionario.get().getNome());
        tarefasResponseDTO.setStatus(status.get().getStatus());

        return tarefasResponseDTO;

    }

    @Transactional
    public TarefasResponseDTO atualizarParcialmenteTarefa(Integer idTarefa, TarefasRequestDTO requestDTO) {
        Tarefas tarefa = repositoryTarefas.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa com ID " + idTarefa + " não encontrada."));

        if (requestDTO.getDescricao() != null) {
            tarefa.setDescricao(requestDTO.getDescricao());
        }

        if (requestDTO.getPrioridade() != null) {
            Prioridade prioridade = repositoryPrioridade.findByNivelCaseInsensitive(requestDTO.getPrioridade());
            if (prioridade == null) {
                throw new EntityNotFoundException("Nível de prioridade '" + requestDTO.getPrioridade() + "' não encontrado.");
            }
            tarefa.setPrioridade(prioridade);
        }

        Tarefas tarefaAtualizada = repositoryTarefas.save(tarefa);


        return objectMapper.convertValue(tarefaAtualizada, TarefasResponseDTO.class);
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
                tarefaExistente.setDataConclusao(LocalDate.now());
            } else if ("pendente".equalsIgnoreCase(requestDTO.getStatus())) {
                tarefaExistente.setIdStatus(1);
                tarefaExistente.setDataConclusao(null);
            } else {
                throw new IllegalArgumentException("Status inválido no PUT. Use 'pendente' ou 'concluida'.");
            }
        } else {
            throw new IllegalArgumentException("O status é obrigatório na atualização completa (PUT).");
        }

        if (requestDTO.getPrioridade() != null) {
            Prioridade prioridade = repositoryPrioridade.findByNivelCaseInsensitive(requestDTO.getPrioridade());
            if (prioridade == null) {
                throw new EntityNotFoundException("Nível de prioridade '" + requestDTO.getPrioridade() + "' não encontrado.");
            }
            tarefaExistente.setPrioridade(prioridade);
        } else {
            throw new IllegalArgumentException("O nível de prioridade é obrigatório na atualização completa (PUT).");
        }

        Tarefas salva = repositoryTarefas.save(tarefaExistente);
        Optional<Status> status = repositoryStatus.findById(salva.getIdStatus());

        TarefasResponseDTO dto = new TarefasResponseDTO();

        dto.setId(salva.getIdTarefa());
        dto.setDescricao(salva.getDescricao());
        dto.setDataCriacao(salva.getDataCriacao());
        dto.setDataConclusao(salva.getDataConclusao());
        dto.setPrioridade(salva.getPrioridade().getNivel());
        dto.setStatus(status.get().getStatus());
        dto.setNome(salva.getIdFuncionario().getNome());

        return dto;
    }

    public ResumoTarefasEtaResponseDTO mostrarResumoTarefasEta(Integer idEta) {
        ResumoTarefasEtaProjection resumoTarefasEtaProjection = repositoryTarefas.resumoTarefasEta(idEta);

        if (resumoTarefasEtaProjection == null) {
            throw new EntityNotFoundException("Nenhuma tarefa encontrada para a ETA: " + idEta);
        }

        ResumoTarefasEtaResponseDTO resumoTarefasEtaResponseDTO = new ResumoTarefasEtaResponseDTO();

        resumoTarefasEtaResponseDTO.setTarefasFeitas(resumoTarefasEtaProjection.getTarefasFeitas());
        resumoTarefasEtaResponseDTO.setTarefasNaoRealizadas(resumoTarefasEtaProjection.getTarefasNaoRealizadas());
        resumoTarefasEtaResponseDTO.setTarefasParaHoje(resumoTarefasEtaProjection.getTarefasParaHoje());
        resumoTarefasEtaResponseDTO.setTarefasTotais(resumoTarefasEtaProjection.getTarefasTotais());

        return resumoTarefasEtaResponseDTO;
    }
}