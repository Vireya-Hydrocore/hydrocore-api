package org.example.hydrocore.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.controller.AvisosController;
import org.example.hydrocore.dto.request.AvisosRequestDTO;
import org.example.hydrocore.dto.response.AvisosResponseDTO;
import org.example.hydrocore.service.AvisosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/avisos")
@Tag(name = "Avisos Controller")
public class AvisosControllerImpl {

    @Autowired
    private AvisosService avisosService;

    @GetMapping("/listar")
    @Operation(summary = "Listar os avisos")
    public ResponseEntity<List<AvisosResponseDTO>> listarAvisos(){
        return ResponseEntity.ok(avisosService.listarAvisos());
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar um aviso")
    public ResponseEntity<AvisosResponseDTO> criarAviso(@RequestBody AvisosRequestDTO aviso) {
        AvisosResponseDTO criado = avisosService.criarAviso(aviso);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar um aviso")
    public ResponseEntity<AvisosResponseDTO> atualizarAviso(@PathVariable("id") Integer id, @RequestBody AvisosRequestDTO requestDTO) {
        AvisosResponseDTO atualizado = avisosService.atualizarAviso(id, requestDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar um aviso")
    public ResponseEntity<AvisosResponseDTO> deletarAviso(@PathVariable("id") Integer id) {
        AvisosResponseDTO avisosResponseDTO = avisosService.deletarAviso(id);
        return ResponseEntity.ok(avisosResponseDTO);
    }


}
