package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.EtaDTO;
import org.example.hydrocore.dto.response.EtaDataRelatorioResponseDTO;
import org.example.hydrocore.dto.response.EtaRelatorioMesResponseDTO;
import org.example.hydrocore.dto.response.EtaResponseDTO;
import org.example.hydrocore.projection.EtaDataRelatorioProjection;
import org.example.hydrocore.projection.EtaRelatorioMesProjection;
import org.example.hydrocore.repository.RepositoryEta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public List<EtaDataRelatorioResponseDTO> listarRelatorioData(Integer idEta) {
        List<EtaDataRelatorioProjection> etaDataRelatorioProjections = repositoryEta.listarDataRelatorio(idEta);

        if (etaDataRelatorioProjections.isEmpty()) {
            throw new EntityNotFoundException("Nenhum Eta encontrado");
        }

        List<EtaDataRelatorioResponseDTO> responseList = new ArrayList<>();

        for (EtaDataRelatorioProjection etaDataRelatorioProjection : etaDataRelatorioProjections) {
            EtaDataRelatorioResponseDTO responseDTO = new EtaDataRelatorioResponseDTO();
            responseDTO.setIdRelatorio(etaDataRelatorioProjection.getIdRelatorio());

            LocalDate data = etaDataRelatorioProjection.getDataProcessoFinal();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("pt", "BR"));
            String mesAno = data.format(formatter);

            mesAno = mesAno.substring(0, 1).toUpperCase() + mesAno.substring(1);

            responseDTO.setData(mesAno);
            responseList.add(responseDTO);
        }

        return responseList;

    }

}