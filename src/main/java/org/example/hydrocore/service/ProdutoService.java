package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.projection.ProdutoProjection;
import org.example.hydrocore.projection.ProdutosUsadosMesProjection;
import org.example.hydrocore.dto.request.ProdutoRequestDTO;
import org.example.hydrocore.dto.response.ProdutoResponseDTO;
import org.example.hydrocore.dto.response.ProdutosUsadosMesResponseDTO;
import org.example.hydrocore.model.Produto;
import org.example.hydrocore.repository.RepositoryProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private RepositoryProduto repositoryProduto;

    @Autowired
    private ObjectMapper objectMapper;

    public List<ProdutoResponseDTO> getAllProdutos() {
        List<ProdutoProjection> all = repositoryProduto.listarProdutos(null);

        if (all.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto cadastrado");
        }

        return all.stream()
                .map(p -> {
                    ProdutoResponseDTO dto = new ProdutoResponseDTO();
                    dto.setId(p.getId());
                    dto.setNome(p.getNomeProduto());
                    dto.setTipo(p.getTipo());
                    dto.setUnidadeMedida(p.getUnidade());
                    return dto;
                })
                .toList();
    }

    public ProdutoResponseDTO getProdutoById(Integer id) {
        List<ProdutoProjection> byId = repositoryProduto.listarProdutos(id);

        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Produto com id " + id + " não encontrado");
        }


        return byId.stream().map(p -> {
            ProdutoResponseDTO dto = new ProdutoResponseDTO();
            dto.setId(p.getId());
            dto.setNome(p.getNomeProduto());
            dto.setTipo(p.getTipo());
            dto.setUnidadeMedida(p.getUnidade());
            return dto;
        }).toList().get(0);

    }

    public ProdutoResponseDTO createProduto(ProdutoRequestDTO produto) {
            Produto save = repositoryProduto.save(objectMapper.convertValue(produto, Produto.class));

            repositoryProduto.findById(save.getIdProduto())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não foi salvo no banco"));

        List<ProdutoProjection> produtoProjections = repositoryProduto.listarProdutos(save.getIdProduto());

        return produtoProjections.stream().map(
                p -> {
                    ProdutoResponseDTO dto = new ProdutoResponseDTO();
                    dto.setId(p.getId());
                    dto.setNome(p.getNomeProduto());
                    dto.setTipo(p.getTipo());
                    dto.setUnidadeMedida(p.getUnidade());
                    return dto;
                }).toList().get(0);

    }

    public ProdutoResponseDTO updateProduto(Integer id, ProdutoRequestDTO produtoRequestDTO) {
        repositoryProduto.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com id " + id + " não encontrado."));

        Produto produtoParaSalvar = objectMapper.convertValue(produtoRequestDTO, Produto.class);

        produtoParaSalvar.setIdProduto(id);
        produtoParaSalvar.setIdUnidadeMedida(produtoRequestDTO.getIdUnidadeMedida());

        Produto produtoSalvo = repositoryProduto.save(produtoParaSalvar);

        List<ProdutoProjection> produtoProjections = repositoryProduto.listarProdutos(produtoSalvo.getIdProduto());

        return produtoProjections.stream().map(p -> {
            ProdutoResponseDTO dto = new ProdutoResponseDTO();
            dto.setId(p.getId());
            dto.setNome(p.getNomeProduto());
            dto.setTipo(p.getTipo());
            dto.setUnidadeMedida(p.getUnidade());
            return dto;
        }).toList().get(0);

    }



    public ProdutoResponseDTO deleteProduto(Integer id) {
        Produto produto = repositoryProduto.deletarProduto(id);

        if (produto == null) {
            throw new EntityNotFoundException("Produto com id " + id + " não encontrado");
        }

        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getIdProduto());
        dto.setNome(produto.getNomeProduto());
        dto.setTipo(produto.getTipo());
        dto.setUnidadeMedida(String.valueOf(produto.getIdUnidadeMedida()));
        return dto;
    }

    public List<ProdutosUsadosMesResponseDTO> getProdutosMaisUsadosMes(Integer mes, Integer ano){
            List<ProdutosUsadosMesProjection> produtos = repositoryProduto.listarProdutosUsadosMes(mes, ano);

            if (produtos.isEmpty()) {
                throw new EntityNotFoundException("Nenhum produto usado no mês " + mes + " de " + ano);
            }

            return produtos.stream().map(p -> {
                        ProdutosUsadosMesResponseDTO pro = new ProdutosUsadosMesResponseDTO();
                        pro.setNomeProduto(p.getNomeProduto());
                        pro.setNomeEta(p.getEtaNome());
                        pro.setTotalUsado(p.getTotalUsado());
                        return pro;
                    }).toList();
    }

}
