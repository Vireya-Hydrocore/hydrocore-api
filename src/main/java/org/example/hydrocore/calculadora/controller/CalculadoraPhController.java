package org.example.hydrocore.calculadora.controller;

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
@RequestMapping("/calculadora/ph")
public class CalculadoraPhController {

    @Autowired
    private CalculadoraPhService service;

    @PostMapping
    public List<CalculoResponseDTO> calcular(@RequestBody CalculoRequestDTO req) {
        return service.calcular(req);
    }
}
