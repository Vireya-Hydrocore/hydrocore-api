package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.EtaDTO;
import org.example.hydrocore.dto.response.EtaRelatorioMesResponseDTO;
import org.example.hydrocore.dto.response.EtaResponseDTO;
import org.example.hydrocore.projection.EtaRelatorioMesProjection;
import org.example.hydrocore.repository.RepositoryEta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EtaService {

    @Autowired
    private RepositoryEta repositoryEta;

    @Autowired
    private ObjectMapper objectMapper;

    public List<EtaResponseDTO> mostrarEtas(){
        List<EtaDTO> etas = repositoryEta.mostrarEtas();

        if (etas.isEmpty()){
            throw new EntityNotFoundException("Nenhum Eta encontrado");
        }

        return etas.stream().map(e -> objectMapper.convertValue(e, EtaResponseDTO.class)).toList();

    }

    public List<EtaRelatorioMesResponseDTO> mostrarRelatorioMes(Integer mes, Integer ano) {
        List<EtaRelatorioMesProjection> etaRelatorioMesDTOS = repositoryEta.gerarRelatorioMes(mes, ano);

        if (etaRelatorioMesDTOS.isEmpty()) {
            throw new EntityNotFoundException("Nenhum Eta encontrado");
        }

        List<EtaRelatorioMesResponseDTO> responseList = new ArrayList<>();

        for (EtaRelatorioMesProjection etaRelatorioMesDTO : etaRelatorioMesDTOS) {
            EtaRelatorioMesResponseDTO responseDTO = new EtaRelatorioMesResponseDTO();
            responseDTO.setId(etaRelatorioMesDTO.getIdEta());
            responseDTO.setVolumeTratado(etaRelatorioMesDTO.getVolumeTratado());
            responseDTO.setNome(etaRelatorioMesDTO.getEtaNome());
            responseDTO.setNomeAdmin(etaRelatorioMesDTO.getEtaAdmin());
            responseDTO.setCidade(etaRelatorioMesDTO.getCidade());
            responseDTO.setEstado(etaRelatorioMesDTO.getEstado());
            responseDTO.setBairro(etaRelatorioMesDTO.getBairro());
            responseDTO.setPhMin(etaRelatorioMesDTO.getPhMin());
            responseDTO.setPhMax(etaRelatorioMesDTO.getPhMax());
            responseDTO.setComentarioGerente(etaRelatorioMesDTO.getComentarioGerente());
            responseList.add(responseDTO);
        }

        return responseList;
    }

}