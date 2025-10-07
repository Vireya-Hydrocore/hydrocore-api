package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.dto.response.EstoqueResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Estoque Controller", description = "Gerenciamento de estoque")
@RequestMapping("/v1/estoque/")
public interface EstoqueController {

    @GetMapping("/mostrar/nomes")
    @Operation(summary = "Listar as informações de estoque com os nomes dos produtos e etas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de produtos que há no estoque",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstoqueResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum produto no estoque foi encontrado")
    })
    ResponseEntity<List<EstoqueResponseDTO>> mostrarEstoqueComNomes();

}
