package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.CalculadoraController;
import org.example.hydrocore.dto.request.CalculadoraCoagulacaoRequestDTO;
import org.example.hydrocore.dto.request.CalculadoraFloculacaoRequestDTO;
import org.example.hydrocore.dto.response.ProdutoDosagemResponseDTO;
import org.example.hydrocore.service.CalculadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculadoraControllerImpl implements CalculadoraController {

    @Autowired
    private CalculadoraService service;

    @Override
    public ResponseEntity<ProdutoDosagemResponseDTO> calcularPhFloculacao(CalculadoraFloculacaoRequestDTO calculoRequestDTO) {
        ProdutoDosagemResponseDTO produtoDosagemResponseDTOS = service.calcularFloculacao(calculoRequestDTO);

        if (produtoDosagemResponseDTOS.getProduto() == null){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(produtoDosagemResponseDTOS);
    }

    @Override
    public ResponseEntity<ProdutoDosagemResponseDTO> calcularPhCoagulacao(CalculadoraCoagulacaoRequestDTO calculoRequestDTO) {
        ProdutoDosagemResponseDTO produtoDosagemResponseDTOS = service.calcularCoagulacao(calculoRequestDTO);

        if (produtoDosagemResponseDTOS.getProduto() == null){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(produtoDosagemResponseDTOS);
    }
}
