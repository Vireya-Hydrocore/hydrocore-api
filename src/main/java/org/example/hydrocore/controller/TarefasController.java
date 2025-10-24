package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.hydrocore.dto.response.ResumoTarefasEtaResponseDTO;
import org.example.hydrocore.dto.request.TarefasCreateRequestDTO;
import org.example.hydrocore.dto.request.TarefasRequestDTO;
import org.example.hydrocore.dto.response.TarefasResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/tarefas")
@Tag(name = "Tarefas controller", description = "Gerenciamento de tarefas")
public interface TarefasController {

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as tarefas", description = "Retorna uma lista de todas as tarefas cadastradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefasResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhuma tarefa encontrada")
    })
    ResponseEntity<List<TarefasResponseDTO>> mostrarTarefas(@RequestParam(required = false) Boolean tarefasPendentes);

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar uma tarefa", description = "Deleta uma tarefa existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa deletada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefasResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    ResponseEntity<TarefasResponseDTO> deletarTarefa(
            @PathVariable("id") Integer idTarefa);

    @GetMapping("/listar-nome/{nome}")
    @Operation(summary = "Listar tarefas do usuário buscando pelo nome", description = "Busca tarefas associadas a um funcionário específico pelo nome.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefasResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhuma tarefa encontrada para o nome especificado")
    })
    ResponseEntity<List<TarefasResponseDTO>> buscarTarefaPorNome(@PathVariable String nome, @RequestParam(required = false) Boolean tarefasConcluidas);

    @PostMapping()
    @Operation(summary = "Criar uma nova tarefa", description = "Cria uma nova tarefa e a associa a um funcionário pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefasResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos")
    })
    ResponseEntity<TarefasResponseDTO> criarTarefa(
            @RequestHeader Integer idFuncionario,
            @RequestBody @Valid TarefasCreateRequestDTO tarefasRequestDTO);

    @PatchMapping("/atualizar-status")
    @Operation(summary = "Atualizar o status da tarefa", description = "Altera o status (ex: PENDENTE, CONCLUÍDA) de uma tarefa existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status da tarefa atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefasResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Tarefa não encontrada ou falha na atualização do status")
    })
    ResponseEntity<TarefasResponseDTO> atualizarStatusTarefa(
            @RequestHeader Integer idTarefa,
            @RequestHeader String status);

    @PatchMapping("/atribuir-tarefa")
    @Operation(summary = "Atribuir a tarefa a outro usuário", description = "Reatribui uma tarefa pendente a um novo funcionário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa reatribuída com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefasResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Tarefa ou funcionário não encontrados")
    })
    ResponseEntity<TarefasResponseDTO> atribuirTarefa(
            @RequestHeader Integer idTarefa,
            @RequestHeader Integer idFuncionario);

    @PatchMapping("/atualizar-parcial/{id}")
    @Operation(summary = "Atualizar parte da tarefa", description = "Atualiza parte da tarefa existente (ex: descrição, data de conclusão).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parte da tarefa atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefasResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Tarefa não encontrada ou falha na atualização")
    })
    ResponseEntity<TarefasResponseDTO> atualizarTarefaParcial(
            @PathVariable("id") Integer idTarefa,
            @RequestBody @Valid TarefasRequestDTO tarefasRequestDTO);

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar a tarefa completamente", description = "Atualzia todos os dados da tarefa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefasResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa ou Prioridade não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos ou campos obrigatórios (descrição, status ou nível) ausentes")
    })
    ResponseEntity<TarefasResponseDTO> atualizarTarefaCompleta(
            @PathVariable("id") Integer idTarefa,
            @RequestBody @Valid TarefasRequestDTO tarefasRequestDTO);

    @GetMapping("/resumo/{idEta}")
    @Operation(summary = "Resumo de tarefas por eta", description = "Retorna o resumo de tarefas por eta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResumoTarefasEtaResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhuma tarefa encontrada para a eta especificada")
    })
    ResponseEntity<ResumoTarefasEtaResponseDTO> resumoTarefasPorEta(@PathVariable Integer idEta);

}
