package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.hydrocore.dto.request.ComentarioGerenteRequestDTO;
import org.example.hydrocore.dto.response.ComentarioGerenteResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comentario Gerente Controller", description = "API para gerenciar comentários de gerentes")
@RequestMapping("/v1/comentario-gerente")
public interface ComentarioGerenteController {

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os comentários", description = "Retorna a lista de comentários cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentários retornados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ComentarioGerenteResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum comentário encontrado")
    })
    ResponseEntity<List<ComentarioGerenteResponseDTO>> listarComentarios();

    @GetMapping("/{id}")
    @Operation(summary = "Buscar comentário por ID", description = "Retorna o comentário cadastrado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentário encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ComentarioGerenteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
    })
    ResponseEntity<ComentarioGerenteResponseDTO> buscarComentarioPorId(@PathVariable Integer id);

    @PostMapping()
    @Operation(summary = "Criar um comentário", description = "Cria um novo comentário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ComentarioGerenteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<ComentarioGerenteResponseDTO> criarComentario(@RequestBody @Valid ComentarioGerenteRequestDTO aviso);

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar um comentário", description = "Atualiza os dados de um comentário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ComentarioGerenteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
    })
    ResponseEntity<ComentarioGerenteResponseDTO> atualizarComentario(@PathVariable("id") Integer id,
                                                      @RequestBody @Valid ComentarioGerenteRequestDTO requestDTO);

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar um comentário", description = "Remove um comentário do sistema pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentário deletado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ComentarioGerenteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
    })
    ResponseEntity<ComentarioGerenteResponseDTO> deletarComentario(@PathVariable("id") Integer id);

}
