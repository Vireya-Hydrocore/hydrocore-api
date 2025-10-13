package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.dto.request.CalculadoraCoagulacaoRequestDTO;
import org.example.hydrocore.dto.request.CalculadoraFloculacaoRequestDTO;
import org.example.hydrocore.dto.response.ProdutoDosagemResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/calculadora")
@Tag(name = "Calculadora Controller", description = "Cálculo de ph baseado na etapa da água")
public interface CalculadoraController {

    @PostMapping("/floculacao")
    @Operation(summary = "Cálculo de ph baseado na floculação", description = "Calcula o pH de acordo com a floculação da água")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cálculo de ph baseado na floculação realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDosagemResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Sem dados para calcular o pH")
    })
    ResponseEntity<ProdutoDosagemResponseDTO> calcularPhFloculacao(@RequestBody CalculadoraFloculacaoRequestDTO calculoRequestDTO);

    @PostMapping("/coagulacao")
    @Operation(summary = "Cálculo de ph baseado na coagulação", description = "Calcula o pH de acordo com a coagulação da água")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cálculo de ph baseado na coagulação realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDosagemResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Sem dados para calcular o pH")
    })
    ResponseEntity<ProdutoDosagemResponseDTO> calcularPhCoagulacao(@RequestBody CalculadoraCoagulacaoRequestDTO calculoRequestDTO);

}
