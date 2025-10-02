package org.example.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.hydrocore.dto.request.ProdutoRequestDTO;
import org.example.hydrocore.dto.response.ProdutoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Produto Controller")
@RequestMapping("/v1/produto")
public interface ProdutoController {

    @GetMapping("/listar")
    @Operation(summary = "Retorna uma lista de produtos", description = "Lista todos os produtos cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado")
    })
    ResponseEntity<List<ProdutoResponseDTO>> listarProdutos();

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um produto por ID", description = "Retorna um produto cadastrado pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto retornado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable("id") Integer id);

    @PostMapping("/criar")
    @Operation(summary = "Cria um produto", description = "Cria um novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    ResponseEntity<ProdutoResponseDTO> criarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO);

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produto", description = "Atualiza um produto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable("id") Integer id, @RequestBody @Valid ProdutoRequestDTO produtoRequestDTO);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um produto", description = "Deleta um produto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ResponseEntity<ProdutoResponseDTO> deletarProduto(@PathVariable("id") Integer id);

}
