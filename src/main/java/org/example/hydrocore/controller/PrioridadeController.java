package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.dto.response.PrioridadeResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1/prioridade")
@Tag(name = "Prioridade Controller", description = "Api para gerenciar as prioridades")
public interface PrioridadeController {

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as prioridades", description = "Retorna uma lista de todas as prioridades cadastradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de prioridades retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PrioridadeResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhuma prioridade encontrada")
    })
    ResponseEntity<List<PrioridadeResponseDTO>> mostrarPrioridades();
}
