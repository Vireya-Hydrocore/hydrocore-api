package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.UnidadeMedidaController;
import org.example.hydrocore.dto.response.UnidadeMedidaResponseDTO;
import org.example.hydrocore.service.UnidadeMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UnidadeMedidaControllerImpl implements UnidadeMedidaController {

    @Autowired
    private UnidadeMedidaService unidadeMedidaService;

    @Override
    public ResponseEntity<List<UnidadeMedidaResponseDTO>> mostrarUnidadesMedida() {
        return ResponseEntity.ok(unidadeMedidaService.mostrarUnidadeMedida());
    }
}
