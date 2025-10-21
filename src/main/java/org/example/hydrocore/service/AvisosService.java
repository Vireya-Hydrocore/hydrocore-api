package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.request.AvisoPatchRequestDTO;
import org.example.hydrocore.dto.request.AvisosRequestDTO;
import org.example.hydrocore.dto.response.AvisosResponseDTO;
import org.example.hydrocore.projection.AvisosProjection;
import org.example.hydrocore.repository.RepositoryAvisos;
import org.example.hydrocore.model.Avisos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvisosService {

    @Autowired
    private RepositoryAvisos repositoryAvisos;

    @Autowired
    private ObjectMapper mapper;

    public List<AvisosResponseDTO> listarAvisos() {
        List<AvisosProjection> all = repositoryAvisos.getAllAvisos(null);

        if (all.isEmpty()) {
            throw new EntityNotFoundException("Nenhum aviso encontrado.");
        }

        return all.stream()
                .map(a -> {
                    AvisosResponseDTO responseDTO = new AvisosResponseDTO();
                    responseDTO.setId(a.getId());
                    responseDTO.setDescricao(a.getDescricao());
                    responseDTO.setDataOcorrencia(a.getDataOcorrencia());
                    responseDTO.setNomeEta(a.getNomeEta());
                    responseDTO.setPrioridade(a.getPrioridade());
                    responseDTO.setStatus(a.getStatus());
                    return responseDTO;
                })
                .collect(Collectors.toList());

    }

    public AvisosResponseDTO criarAviso(AvisosRequestDTO aviso) {
        Avisos avisoEntity = mapper.convertValue(aviso, Avisos.class);
        Avisos salvo = repositoryAvisos.save(avisoEntity);

        if (salvo == null) {
            throw new CannotCreateTransactionException("O aviso não foi salvo, possivelmente devido a um erro de transação.");
        }

        List<AvisosProjection> allAvisos = repositoryAvisos.getAllAvisos(salvo.getId());

        return allAvisos.stream().map(p -> {
            AvisosResponseDTO responseDTO = new AvisosResponseDTO();
            responseDTO.setId(p.getId());
            responseDTO.setDescricao(p.getDescricao());
            responseDTO.setDataOcorrencia(p.getDataOcorrencia());
            responseDTO.setNomeEta(p.getNomeEta());
            responseDTO.setPrioridade(p.getPrioridade());
            responseDTO.setStatus(p.getStatus());
            return responseDTO;
        }).toList().get(0);

    }

    public AvisosResponseDTO atualizarAviso(Integer id, AvisosRequestDTO novoAviso) {
        Avisos existente = repositoryAvisos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aviso com ID " + id + " não encontrado para atualização."));

        existente.setDescricao(novoAviso.getDescricao());
        existente.setDataOcorrencia(novoAviso.getDataOcorrencia());
        existente.setIdEta(novoAviso.getIdEta());
        existente.setIdPrioridade(novoAviso.getIdPrioridade());

        Avisos atualizado = repositoryAvisos.save(existente);

        List<AvisosProjection> allAvisos = repositoryAvisos.getAllAvisos(atualizado.getId());

        return allAvisos.stream().map(p -> {
            AvisosResponseDTO responseDTO = new AvisosResponseDTO();
            responseDTO.setId(p.getId());
            responseDTO.setDescricao(p.getDescricao());
            responseDTO.setDataOcorrencia(p.getDataOcorrencia());
            responseDTO.setNomeEta(p.getNomeEta());
            responseDTO.setPrioridade(p.getPrioridade());
            responseDTO.setStatus(p.getStatus());
            return responseDTO;
        }).toList().get(0);

    }

    public AvisosResponseDTO deletarAviso(Integer id) {
        Avisos avisoParaRetorno = repositoryAvisos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id + " não encontrado para exclusão."));

        Avisos avisos = repositoryAvisos.deleteByIdAvisos(id);

        List<AvisosProjection> allAvisos = repositoryAvisos.getAllAvisos(avisos.getId());

        return allAvisos.stream().map(p -> {
            AvisosResponseDTO responseDTO = new AvisosResponseDTO();
            responseDTO.setId(p.getId());
            responseDTO.setDescricao(p.getDescricao());
            responseDTO.setDataOcorrencia(p.getDataOcorrencia());
            responseDTO.setNomeEta(p.getNomeEta());
            responseDTO.setPrioridade(p.getPrioridade());
            responseDTO.setStatus(p.getStatus());
            return responseDTO;
        }).toList().get(0);

    }

    public AvisosResponseDTO atualizarAvisoParcial(Integer id, AvisoPatchRequestDTO avisoParcial) {
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

        List<AvisosProjection> allAvisos = repositoryAvisos.getAllAvisos(atualizado.getId());

        return allAvisos.stream().map(p -> {
            AvisosResponseDTO responseDTO = new AvisosResponseDTO();
            responseDTO.setId(p.getId());
            responseDTO.setDescricao(p.getDescricao());
            responseDTO.setDataOcorrencia(p.getDataOcorrencia());
            responseDTO.setNomeEta(p.getNomeEta());
            responseDTO.setPrioridade(p.getPrioridade());
            responseDTO.setStatus(p.getStatus());
            return responseDTO;
        }).toList().get(0);

    }

    public List<AvisosResponseDTO> listarAvisosPorData(LocalDate dataReferencia){
        List<AvisosProjection> avisosByData = repositoryAvisos.getAvisosByData(dataReferencia);

        if (avisosByData.isEmpty()){
            throw new EntityNotFoundException("Nenhum aviso encontrado para os ultimos 6 dias da data " + dataReferencia);
        }

        return avisosByData.stream().map(p -> {
            AvisosResponseDTO responseDTO = new AvisosResponseDTO();
            responseDTO.setId(p.getId());
            responseDTO.setDescricao(p.getDescricao());
            responseDTO.setDataOcorrencia(p.getDataOcorrencia());
            responseDTO.setNomeEta(p.getNomeEta());
            responseDTO.setPrioridade(p.getPrioridade());
            responseDTO.setStatus(p.getStatus());
            return responseDTO;
        }).toList();

    }

}