package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.dto.request.TarefasRequestDTO;
import org.example.hydrocore.dto.response.TarefasResponseDTO;
import org.example.hydrocore.service.TarefasService;
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

    @GetMapping("/atualizar-status")
    @Operation(summary = "Atualizar o status da tarefa")
    public ResponseEntity<TarefasResponseDTO> atualizarStatusTarefa(
            @RequestHeader Long idTarefa,
            @RequestHeader String status){

        TarefasResponseDTO tarefasResponseDTO = tarefasService.atualizarStatusTarefa(idTarefa, status);

        if (tarefasResponseDTO!= null){
            return ResponseEntity.ok(tarefasResponseDTO);
        }

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/atribuir-tarefa")
    @Operation(summary = "Atribuir a tarefa a outro usuário")
    public ResponseEntity<TarefasResponseDTO> atribuirTarefa(
            @RequestHeader Long idTarefa,
            @RequestHeader Long idFuncionario) {

        TarefasResponseDTO tarefasResponseDTO = tarefasService.atribuirTarefa(idTarefa, idFuncionario);

        if (tarefasResponseDTO!= null){
            return ResponseEntity.ok(tarefasResponseDTO);
        }

        return ResponseEntity.noContent().build();

    }

}
