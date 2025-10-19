package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.hydrocore.dto.request.AvisoPatchRequestDTO;
import org.example.hydrocore.dto.request.AvisosRequestDTO;
import org.example.hydrocore.dto.response.AvisosResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Avisos Controller", description = "Gerenciamento de avisos")
@RequestMapping("/v1/avisos")
public interface AvisosController {

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os avisos", description = "Retorna a lista de avisos cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvisosResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum aviso encontrado")
    })
    ResponseEntity<List<AvisosResponseDTO>> listarAvisos();

    @PostMapping()
    @Operation(summary = "Criar um aviso", description = "Cria um novo aviso no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aviso criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvisosResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<AvisosResponseDTO> criarAviso(@RequestBody @Valid AvisosRequestDTO aviso);

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar um aviso", description = "Atualiza os dados de um aviso existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aviso atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvisosResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Aviso não encontrado")
    })
    ResponseEntity<AvisosResponseDTO> atualizarAviso(@PathVariable("id") Integer id,
                                                     @RequestBody @Valid AvisosRequestDTO requestDTO);

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar um aviso", description = "Remove um aviso do sistema pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aviso deletado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvisosResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Aviso não encontrado")
    })
    ResponseEntity<AvisosResponseDTO> deletarAviso(@PathVariable("id") Integer id);

    @PatchMapping("/atualizar-parcial/{id}")
    @Operation(summary = "Atualizar parcialmente um aviso", description = "Atualiza parcialmente os dados de um aviso existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aviso atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvisosResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Aviso não encontrado")
    })
    ResponseEntity<AvisosResponseDTO> atualizarParcialmenteAviso(@PathVariable("id") Integer id,
                                                      @RequestBody @Valid AvisoPatchRequestDTO requestDTO);

}

