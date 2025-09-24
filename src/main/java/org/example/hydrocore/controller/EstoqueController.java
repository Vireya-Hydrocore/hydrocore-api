package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.dto.response.EstoqueResponseDTO;
import org.example.hydrocore.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/estoque")
@Tag(name = "Estoque controller")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @Operation(summary = "Listar as informações de estoque com os nomes dos produtos e etas")
    @GetMapping("/mostrar/nomes")
    public ResponseEntity<List<EstoqueResponseDTO>> mostrarEstoqueComNomes(){
        List<EstoqueResponseDTO> estoqueResponseDTOS = estoqueService.mostrarEstoqueComNome();

        if (estoqueResponseDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(estoqueResponseDTOS);
    }

}
