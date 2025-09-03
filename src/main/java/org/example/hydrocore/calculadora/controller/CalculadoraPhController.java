package org.example.hydrocore.calculadora.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.calculadora.dto.request.CalculoRequestDTO;
import org.example.hydrocore.calculadora.dto.response.CalculoResponseDTO;
import org.example.hydrocore.calculadora.service.CalculadoraPhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/calculadora/ph")
@Tag(name = "Calculadora pH Controller")
public class CalculadoraPhController {

    @Autowired
    private CalculadoraPhService service;

    @Operation(summary = "Calcula a quantidade de produto necessária para atingir o pH desejado")
    @PostMapping("/calcular")
    public List<CalculoResponseDTO> calcular(@RequestBody CalculoRequestDTO req) {
        return service.calcular(req);
    }
}
