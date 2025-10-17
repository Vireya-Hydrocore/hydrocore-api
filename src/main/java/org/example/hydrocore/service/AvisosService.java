package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.request.AvisoPatchRequestDTO;
import org.example.hydrocore.dto.request.AvisosRequestDTO;
import org.example.hydrocore.dto.response.AvisosResponseDTO;
import org.example.hydrocore.repository.RepositoryAvisos;
import org.example.hydrocore.model.Avisos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvisosService {

    @Autowired
    private RepositoryAvisos repositoryAvisos;

    @Autowired
    private ObjectMapper mapper;

    public List<AvisosResponseDTO> listarAvisos() {
        try {
            List<Avisos> all = repositoryAvisos.findAll();

            if (all.isEmpty()) {
                throw new EntityNotFoundException("Nenhum aviso encontrado.");
            }

            return all.stream()
                    .map(a -> mapper.convertValue(a, AvisosResponseDTO.class))
                    .collect(Collectors.toList());

        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para listar avisos.", e);
        }
    }

    public AvisosResponseDTO criarAviso(AvisosRequestDTO aviso) {
        try {
            Avisos avisoEntity = mapper.convertValue(aviso, Avisos.class);
            Avisos salvo = repositoryAvisos.save(avisoEntity);

            if (salvo == null) {
                throw new CannotCreateTransactionException("O aviso não foi salvo, possivelmente devido a um erro de transação.");
            }

            return mapper.convertValue(salvo, AvisosResponseDTO.class);

        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao salvar o aviso no banco de dados.", e);
        }
    }

    public AvisosResponseDTO atualizarAviso(Integer id, AvisosRequestDTO novoAviso) {
        try {
            Avisos existente = repositoryAvisos.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Aviso com ID " + id + " não encontrado para atualização."));

            existente.setDescricao(novoAviso.getDescricao());
            existente.setDataOcorrencia(novoAviso.getDataOcorrencia());
            existente.setIdEta(novoAviso.getIdEta());
            existente.setIdPrioridade(novoAviso.getIdPrioridade());

            Avisos atualizado = repositoryAvisos.save(existente);

            return mapper.convertValue(atualizado, AvisosResponseDTO.class);

        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao acessar ou salvar no banco de dados durante a atualização do aviso.", e);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    public AvisosResponseDTO deletarAviso(Integer id) {
        try {
            Avisos avisoParaRetorno = repositoryAvisos.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(id + " não encontrado para exclusão."));

            repositoryAvisos.deleteByIdAvisos(id);

            return mapper.convertValue(avisoParaRetorno, AvisosResponseDTO.class);

        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao tentar deletar o aviso no banco de dados.", e);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    public AvisosResponseDTO atualizarAvisoParcial(Integer id, AvisoPatchRequestDTO avisoParcial) {
        try {
            Avisos existente = repositoryAvisos.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Aviso com ID " + id + " não encontrado para atualização."));

            if (avisoParcial.getDescricao() != null) {
                existente.setDescricao(avisoParcial.getDescricao());
            }
            if (avisoParcial.getDataOcorrencia() != null) {
                existente.setDataOcorrencia(avisoParcial.getDataOcorrencia());
            }
            if (avisoParcial.getIdEta() != null) {
                existente.setIdEta(avisoParcial.getIdEta());
            }
            if (avisoParcial.getIdPrioridade() != null) {
                existente.setIdPrioridade(avisoParcial.getIdPrioridade());
            }

            Avisos atualizado = repositoryAvisos.save(existente);

            return mapper.convertValue(atualizado, AvisosResponseDTO.class);

        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao acessar ou salvar no banco de dados durante a atualização parcial do aviso.", e);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

}