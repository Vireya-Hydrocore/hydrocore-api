package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.CargoController;
import org.example.hydrocore.dto.response.CargoResponseDTO;
import org.example.hydrocore.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CargoControllerImpl implements CargoController {

    @Autowired
    private CargoService cargoService;

    @Override
    public ResponseEntity<List<CargoResponseDTO>> listarCargos() {
        List<CargoResponseDTO> cargos = cargoService.listarCargos();
        if (cargos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cargos);
    }

    @Override
    public ResponseEntity<CargoResponseDTO> buscarCargoPorId(Integer id) {
        CargoResponseDTO cargo = cargoService.buscarCargoPorId(id);
        return ResponseEntity.ok(cargo);
    }

}