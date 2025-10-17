package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.ProdutoController;
import org.example.hydrocore.dto.request.ProdutoRequestDTO;
import org.example.hydrocore.dto.response.ProdutoResponseDTO;
import org.example.hydrocore.dto.response.ProdutosUsadosMesResponseDTO;
import org.example.hydrocore.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProdutoControllerImpl implements ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Override
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.getAllProdutos());
    }

    @Override
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(Integer id) {
        return ResponseEntity.ok(produtoService.getProdutoById(id));
    }

    @Override
    public ResponseEntity<ProdutoResponseDTO> criarProduto(ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.ok(produtoService.createProduto(produtoRequestDTO));
    }

    @Override
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(Integer id, ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.ok(produtoService.updateProduto(id, produtoRequestDTO));
    }

    @Override
    public ResponseEntity<ProdutoResponseDTO> deletarProduto(Integer id) {
        return ResponseEntity.ok(produtoService.deleteProduto(id));
    }

    @Override
    public ResponseEntity<List<ProdutosUsadosMesResponseDTO>> produtosMaisUsados(Integer mes, Integer ano) {
        return ResponseEntity.ok(produtoService.getProdutosMaisUsadosMes(mes, ano));
    }

}
