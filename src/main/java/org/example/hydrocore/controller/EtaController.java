package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.dto.response.EtaRelatorioMesResponseDTO;
import org.example.hydrocore.dto.response.EtaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Eta Controller", description = "Gerenciamento de etas")
@RequestMapping("/v1/eta/")
public interface EtaController {

    @GetMapping("/listar")
    @Operation(summary = "Listar as informações de eta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de etas existentes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EtaResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhuma eta cadastrada")
    })
    ResponseEntity<List<EtaResponseDTO>> mostrarEtas();

    @GetMapping("/relatorio")
    @Operation(summary = "Gerar relatório de estoque por eta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EtaRelatorioMesResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum estoque cadastrado para essa eta")
    })
    ResponseEntity<List<EtaRelatorioMesResponseDTO>> gerarRelatorioMensal(Integer mes, Integer ano);

}
