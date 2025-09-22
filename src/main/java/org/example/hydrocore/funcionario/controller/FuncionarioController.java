package org.example.hydrocore.funcionario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.funcionario.dto.FuncionarioRelatorioDTO;
import org.example.hydrocore.funcionario.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Funcionario Controller")
@RequestMapping("/v1/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService  funcionarioService;

    @GetMapping("/listar-funcionarios")
    @Operation(summary = "Listar todos os funcionarios")
    public ResponseEntity<List<FuncionarioRelatorioDTO>> listarFuncionarios() {
        List<FuncionarioRelatorioDTO> allFuncionarios = funcionarioService.getAllFuncionarios();

        if (allFuncionarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allFuncionarios);

    }


}
