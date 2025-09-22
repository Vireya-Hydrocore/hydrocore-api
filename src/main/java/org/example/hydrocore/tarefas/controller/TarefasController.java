package org.example.hydrocore.tarefas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.tarefas.dto.request.TarefasRequestDTO;
import org.example.hydrocore.tarefas.dto.response.TarefasResponseDTO;
import org.example.hydrocore.tarefas.service.TarefasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tarefas")
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

    @GetMapping("/listar-nome/{nome}")
    @Operation(summary = "Listar tarefas do usuário buscando pelo nome")
    public ResponseEntity<List<TarefasResponseDTO>> buscarTarefaPorNome(@PathVariable String nome){
        List<TarefasResponseDTO> tarefasResponseDTOS = tarefasService.buscarTarefaPorNome(nome);

        if (tarefasResponseDTOS.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tarefasResponseDTOS);
    }

    @PostMapping("/criar-tarefa")
    @Operation(summary = "Criar uma nova tarefa")
    public ResponseEntity<TarefasResponseDTO> criarTarefa(
            @RequestHeader Long idFuncionario,
            @RequestBody TarefasRequestDTO tarefasRequestDTO){

        Boolean tarefa = tarefasService.criarTarefa(idFuncionario, tarefasRequestDTO);

        if (tarefa==true){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();

    }

}
