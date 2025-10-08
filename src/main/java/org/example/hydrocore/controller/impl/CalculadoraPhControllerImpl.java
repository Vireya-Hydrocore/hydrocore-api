package org.example.hydrocore.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.controller.CalculadoraPhController;
import org.example.hydrocore.dto.request.CalculoRequestDTO;
import org.example.hydrocore.dto.response.CalculoResponseDTO;
import org.example.hydrocore.service.CalculadoraPhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CalculadoraPhControllerImpl implements CalculadoraPhController {

    @Autowired
    private CalculadoraPhService service;

    @Override
    public ResponseEntity<CalculoResponseDTO> calcularPhFloculacao(CalculoRequestDTO calculoRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CalculoResponseDTO> calcularPhCoagulacao(CalculoRequestDTO calculoRequestDTO) {
        return null;
    }
}
