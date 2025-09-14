package org.example.hydrocore.tarefas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.tarefas.dto.response.TarefasResponseDTO;
import org.example.hydrocore.tarefas.service.TarefasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@Tag(name = "Tarefas controller")
public class TarefasController {

    @Autowired
    private TarefasService tarefasService;

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as tarefas")
    public ResponseEntity<List<TarefasResponseDTO>> mostrarTarefas(){
        List<TarefasResponseDTO> tarefasResponseDTOS = tarefasService.listarTarefas();

        if (tarefasResponseDTOS.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tarefasResponseDTOS);
    }



}
