package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.dto.response.CargoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cargo Controller", description = "Gerenciamento de cargos")
@RequestMapping("/v1/cargo")
public interface CargoController {

    @GetMapping ("/listar")
    @Operation(summary = "Listar todos os cargos", description = "Retorna a lista de cargos cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargos retornados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CargoResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum cargo encontrado")
    })
    ResponseEntity<List<CargoResponseDTO>> listarCargos();

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cargo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CargoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado")
    })
    ResponseEntity<CargoResponseDTO> buscarCargoPorId(@PathVariable Integer id);

}