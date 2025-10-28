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
        List<PrioridadeResponseDTO> prioridades = prioridadeService.mostrarPrioridade();

        if (prioridades.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não houver dados
        }

        return ResponseEntity.ok(prioridades);
    }
}
