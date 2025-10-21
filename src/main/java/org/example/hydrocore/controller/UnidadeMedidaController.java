package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.dto.response.UnidadeMedidaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1/unidade-medida")
@Tag(name = "Unidade de Medida Controller", description = "Api para gerenciar as unidades de medida")
public interface UnidadeMedidaController {

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as unidades de medida", description = "Retorna uma lista de todas as unidades de medida cadastradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de unidades de medida retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeMedidaResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhuma unidade de medida encontrada")
    })
    ResponseEntity<List<UnidadeMedidaResponseDTO>> mostrarUnidadesMedida();




}
