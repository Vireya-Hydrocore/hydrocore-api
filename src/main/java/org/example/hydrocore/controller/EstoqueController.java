package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hydrocore.dto.response.EstoqueResponseDTO;
import org.example.hydrocore.dto.response.ProdutoEtaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Estoque Controller", description = "Gerenciamento de estoque")
@RequestMapping("/v1/estoque/")
public interface EstoqueController {

    @GetMapping("/listar")
    @Operation(summary = "Listar as informações de estoque com os nomes dos produtos e etas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de produtos que há no estoque",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstoqueResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum produto no estoque foi encontrado")
    })
    ResponseEntity<List<EstoqueResponseDTO>> mostrarEstoqueComNomes(@RequestHeader(required = false) String nome);

    @GetMapping("/listar/produtos")
    @Operation(summary = "Listar a quantidade de produtos por eta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista os produtos por eta",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoEtaResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum produto no estoque foi encontrado")
    })
    ResponseEntity<List<ProdutoEtaResponseDTO>> mostarQuantidadeProdutosPorEta(@RequestHeader(required = false) String nome);

    @PostMapping("/adicionar")
    @Operation(summary = "Adicionar produtos ao estoque")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Adicionado produtos ao estoque",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstoqueResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum produto no estoque foi encontrado")
    })
    ResponseEntity<EstoqueResponseDTO> adicionarProdutosAoEstoque(@RequestHeader Integer idProduto, @RequestHeader Integer idEta, @RequestHeader BigDecimal quantidade);

    @PostMapping("/remover")
    @Operation(summary = "Remover produtos ao estoque")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Removendo produtos ao estoque",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstoqueResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum produto no estoque foi encontrado")
    })
    ResponseEntity<EstoqueResponseDTO> removerProdutosDoEstoque(@RequestHeader Integer idProduto, @RequestHeader Integer idEta, @RequestHeader BigDecimal quantidade);

}
