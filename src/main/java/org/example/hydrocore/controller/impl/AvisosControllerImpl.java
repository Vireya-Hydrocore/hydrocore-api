package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.AvisosController;
import org.example.hydrocore.dto.request.AvisoPatchRequestDTO;
import org.example.hydrocore.dto.request.AvisosRequestDTO;
import org.example.hydrocore.dto.response.AvisosResponseDTO;
import org.example.hydrocore.service.AvisosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<AvisosResponseDTO> criarAviso(AvisosRequestDTO aviso) {
        return ResponseEntity.ok(avisosService.criarAviso(aviso));
    }

    @Override
    public ResponseEntity<AvisosResponseDTO> atualizarAviso(Integer id, AvisosRequestDTO requestDTO) {
        return ResponseEntity.ok(avisosService.atualizarAviso(id, requestDTO));
    }

    @Override
    public ResponseEntity<AvisosResponseDTO> deletarAviso(Integer id) {
        return ResponseEntity.ok(avisosService.deletarAviso(id));
    }

    @Override
    public ResponseEntity<AvisosResponseDTO> atualizarParcialmenteAviso(Integer id, AvisoPatchRequestDTO requestDTO) {
        return ResponseEntity.ok(avisosService.atualizarAvisoParcial(id, requestDTO));
    }

}
