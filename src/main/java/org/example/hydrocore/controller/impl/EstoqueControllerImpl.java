package org.example.hydrocore.controller.impl;

import org.example.hydrocore.controller.EstoqueController;
import org.example.hydrocore.dto.response.EstoqueResponseDTO;
import org.example.hydrocore.dto.response.ProdutoEtaResponseDTO;
import org.example.hydrocore.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class EstoqueControllerImpl implements EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    public ResponseEntity<List<EstoqueResponseDTO>> mostrarEstoqueComNomes(String nome){
        List<EstoqueResponseDTO> estoqueResponseDTOS = estoqueService.mostrarEstoqueComNome(nome);

        if (estoqueResponseDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(estoqueResponseDTOS);
    }

    @Override
    public ResponseEntity<List<ProdutoEtaResponseDTO>> mostarQuantidadeProdutosPorEta(String nome) {
        List<ProdutoEtaResponseDTO> produtoEtaResponseDTOS = estoqueService.mostrarTotalProdutosPorEta(nome);

        if (produtoEtaResponseDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(produtoEtaResponseDTOS);
    }

    @Override
    public ResponseEntity<Void> adicionarProdutosAoEstoque(Integer idProduto, Integer idEta, BigDecimal quantidade) {
        estoqueService.adicionarProdutoEstoque(idProduto, idEta, quantidade);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removerProdutosDoEstoque(Integer idProduto, Integer idEta, BigDecimal quantidade) {
        estoqueService.removerProdutoEstoque(idProduto, idEta, quantidade);
        return ResponseEntity.ok().build();
    }

}
