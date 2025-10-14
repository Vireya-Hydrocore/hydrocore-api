package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Health Controller", description = "Verificar se o servidor está online")
@RequestMapping("/v1/health")
public interface HealthController {

    @GetMapping()
    @Operation(summary = "Verificar se o servidor está online", description = "Verifica se o servidor está online e retorna uma mensagem de sucesso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servidor online",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Servidor offline")
    })
    ResponseEntity<String> healthCheck();


}
