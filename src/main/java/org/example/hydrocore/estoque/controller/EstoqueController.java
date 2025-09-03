package org.example.hydrocore.estoque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.estoque.dto.EstoqueDTO;
import org.example.hydrocore.repository.RepositoryEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/estoque")
@Tag(name = "Estoque controller")
public class EstoqueController {

    @Autowired
    RepositoryEstoque repositoryEstoque;

    @Operation(summary = "Listar as informações de estoque com os nomes dos produtos e etas")
    @GetMapping("/mostrar/nomes")
    public List<EstoqueDTO> mostrarEstoqueComNomes(){
        return repositoryEstoque.findAllEstoqueComNomes();
    }

}
