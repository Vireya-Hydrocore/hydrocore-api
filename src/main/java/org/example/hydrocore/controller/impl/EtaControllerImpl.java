package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.EtaController;
import org.example.hydrocore.dto.response.EtaDataRelatorioResponseDTO;
import org.example.hydrocore.dto.response.EtaRelatorioMesResponseDTO;
import org.example.hydrocore.dto.response.EtaResponseDTO;
import org.example.hydrocore.service.EtaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EtaControllerImpl implements EtaController {

    @Autowired
    private EtaService etaService;

    @Override
    public ResponseEntity<List<EtaResponseDTO>> mostrarEtas() {
        return ResponseEntity.ok(etaService.mostrarEtas());
    }

    @Override
    public ResponseEntity<List<EtaRelatorioMesResponseDTO>> gerarRelatorioMensal(Integer mes, Integer ano) {
        return ResponseEntity.ok(etaService.mostrarRelatorioMes(mes, ano));
    }

    @Override
    public ResponseEntity<List<EtaDataRelatorioResponseDTO>> listarRelatorioData(Integer idEta) {
        return ResponseEntity.ok(etaService.listarRelatorioData(idEta));
    }

}
