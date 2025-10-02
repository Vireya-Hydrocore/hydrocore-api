package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.dto.request.FuncionarioPatchRequestDTO;
import org.example.hydrocore.dto.request.FuncionarioRequestDTO;
import org.example.hydrocore.dto.response.FuncionarioIdResponseDTO;
import org.example.hydrocore.dto.response.FuncionarioResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Funcionario Controller")
@RequestMapping("/v1/funcionario")
public interface FuncionarioController {

    @GetMapping("/listar")
    @Operation(summary = "Retorna uma lista de funcionários", description = "Lista todos os funcionários cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum funcionário encontrado")
    })
    ResponseEntity<List<FuncionarioResponseDTO>> listarFuncionarios();

    @GetMapping("/listar-id/{id}")
    @Operation(summary = "Retorna um funcionário por ID", description = "Busca um funcionário específico pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    ResponseEntity<FuncionarioResponseDTO> getFuncionarioById(@PathVariable Integer id);

    @PostMapping("/salvar")
    @Operation(summary = "Salva um novo funcionário", description = "Cria um novo funcionário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<FuncionarioResponseDTO> salvarFuncionario(@Valid @RequestBody FuncionarioRequestDTO requestDTO);

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualiza um funcionário existente", description = "Atualiza todos os dados de um funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    ResponseEntity<FuncionarioResponseDTO> atualizarFuncionario(@PathVariable Integer id,
                                                                @Valid @RequestBody FuncionarioRequestDTO requestDTO);

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta um funcionário", description = "Remove um funcionário do sistema pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário deletado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    ResponseEntity<FuncionarioResponseDTO> deletarFuncionario(@PathVariable Integer id);

    @GetMapping("/email")
    @Operation(summary = "Retorna o id do funcionario", description = "Busca id do funcionario pelo email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionario encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioIdResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    ResponseEntity<FuncionarioIdResponseDTO> buscarIdFuncionarioPorEmail(@RequestHeader("email") String email);

    @PatchMapping("/atualizar-parcial/{id}")
    @Operation(summary = "Atualiza um funcionário existente", description = "Atualiza parcialmente os dados de um funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    ResponseEntity<FuncionarioResponseDTO> atualizarFuncionarioParcial(@PathVariable Integer id,
                                                                @Valid @RequestBody FuncionarioPatchRequestDTO requestDTO);

}
