package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.AvisosController;
import org.example.hydrocore.dto.request.AvisoPatchRequestDTO;
import org.example.hydrocore.dto.request.AvisosRequestDTO;
import org.example.hydrocore.dto.response.AvisoIdResponseDTO;
import org.example.hydrocore.dto.response.AvisosHojeResponseDTO;
import org.example.hydrocore.dto.response.AvisosResponseDTO;
import org.example.hydrocore.service.AvisosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class AvisosControllerImpl implements AvisosController {

    @Autowired
    private AvisosService avisosService;

    @Override
    public ResponseEntity<List<AvisosResponseDTO>> listarAvisos() {
        return ResponseEntity.ok(avisosService.listarAvisos());
    }

    @Override
    public ResponseEntity<AvisoIdResponseDTO> criarAviso(AvisosRequestDTO aviso) {
        AvisoIdResponseDTO avisosResponseDTO = avisosService.criarAviso(aviso);

        return new ResponseEntity<>(avisosResponseDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AvisoIdResponseDTO> atualizarAviso(Integer id, AvisosRequestDTO requestDTO) {
        return ResponseEntity.ok(avisosService.atualizarAviso(id, requestDTO));
    }

    @Override
    public ResponseEntity<AvisoIdResponseDTO> deletarAviso(Integer id) {
        return ResponseEntity.ok(avisosService.deletarAviso(id));
    }

    @Override
    public ResponseEntity<AvisoIdResponseDTO> atualizarParcialmenteAviso(Integer id, AvisoPatchRequestDTO requestDTO) {
        return ResponseEntity.ok(avisosService.atualizarAvisoParcial(id, requestDTO));
    }

    @Override
    public ResponseEntity<List<AvisosResponseDTO>> listarUltimosAvisos(LocalDate dataReferencia) {
        return ResponseEntity.ok(avisosService.listarAvisosPorData(dataReferencia));
    }

    @Override
    public ResponseEntity<AvisosResponseDTO> listarAvisoPorId(Integer id) {
        return ResponseEntity.ok(avisosService.listarAvisosPorId(id));
    }

    @Override
    public ResponseEntity<List<AvisosHojeResponseDTO>> listarAvisosHoje() {
        return ResponseEntity.ok(avisosService.listarAvisosPorHoje());
    }

}
