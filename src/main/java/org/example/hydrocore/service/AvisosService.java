package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.request.AvisosRequestDTO;
import org.example.hydrocore.dto.response.AvisosResponseDTO;
import org.example.hydrocore.repository.RepositoryAvisos;
import org.example.hydrocore.repository.entity.Avisos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.List;

@Service
public class AvisosService {

    @Autowired
    private RepositoryAvisos repositoryAvisos;

    @Autowired
    private ObjectMapper mapper;

    public List<AvisosResponseDTO> listarAvisos(){
        List<Avisos> all = repositoryAvisos.findAll();

        if (all.isEmpty()){
            throw new EntityNotFoundException("Nenhum aviso encontrado");
        }

        return all.stream().map(a -> mapper.convertValue(a, AvisosResponseDTO.class)).toList();

    }

    public AvisosResponseDTO criarAviso(AvisosRequestDTO aviso) {
        Avisos salvo = repositoryAvisos.save(mapper.convertValue(aviso, Avisos.class));

        if (salvo == null){
            throw new CannotCreateTransactionException("Nenhum aviso salvo");
        }

        return mapper.convertValue(salvo, AvisosResponseDTO.class);
    }

    public AvisosResponseDTO atualizarAviso(Long id, AvisosRequestDTO novoAviso) {
        Avisos existente = repositoryAvisos.findById(id)
                .orElseThrow(() -> new RuntimeException("Aviso não encontrado"));

        existente.setDescricao(novoAviso.getDescricao());
        existente.setDataOcorrencia(novoAviso.getDataOcorrencia());
        existente.setIdEta(novoAviso.getIdEta());
        existente.setIdPrioridade(novoAviso.getIdPrioridade());

        Avisos atualizado = repositoryAvisos.save(existente);
        return mapper.convertValue(atualizado, AvisosResponseDTO.class);

    }

}
