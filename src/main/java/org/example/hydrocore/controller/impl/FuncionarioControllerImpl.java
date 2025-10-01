package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.FuncionarioController;
import org.example.hydrocore.dto.request.FuncionarioRequestDTO;
import org.example.hydrocore.dto.response.FuncionarioResponseDTO;
import org.example.hydrocore.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FuncionarioControllerImpl implements FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Override
    public ResponseEntity<List<FuncionarioResponseDTO>> listarFuncionarios() {
        List<FuncionarioResponseDTO> all = funcionarioService.getAllFuncionarios();
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<FuncionarioResponseDTO> getFuncionarioById(Integer id) {
        return ResponseEntity.ok(funcionarioService.getFuncionarioById(id));
    }

    @Override
    public ResponseEntity<FuncionarioResponseDTO> salvarFuncionario(FuncionarioRequestDTO requestDTO) {
        return ResponseEntity.ok(funcionarioService.salvarFuncionario(requestDTO));
    }

    @Override
    public ResponseEntity<FuncionarioResponseDTO> atualizarFuncionario(Integer id, FuncionarioRequestDTO requestDTO) {
        return ResponseEntity.ok(funcionarioService.atualizarFuncionario(id, requestDTO));
    }

    @Override
    public ResponseEntity<FuncionarioResponseDTO> deletarFuncionario(Integer id) {
        return ResponseEntity.ok(funcionarioService.deletarFuncionario(id));
    }
}
