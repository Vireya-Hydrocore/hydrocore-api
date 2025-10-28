package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.PrioridadeController;
import org.example.hydrocore.dto.response.PrioridadeResponseDTO;
import org.example.hydrocore.service.PrioridadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PrioridadeControllerImpl implements PrioridadeController {

    @Autowired
    private PrioridadeService prioridadeService;

    @Override
    public ResponseEntity<List<PrioridadeResponseDTO>> mostrarPrioridades() {
        return ResponseEntity.ok(prioridadeService.mostrarPrioridade());
    }
}
