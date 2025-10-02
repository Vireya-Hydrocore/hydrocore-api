package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.request.ProdutoRequestDTO;
import org.example.hydrocore.dto.response.ProdutoResponseDTO;
import org.example.hydrocore.repository.RepositoryProduto;
import org.example.hydrocore.repository.entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private RepositoryProduto repositoryProduto;

    @Autowired
    private ObjectMapper objectMapper;

    public List<ProdutoResponseDTO> getAllProdutos(){
        List<Produto> all = repositoryProduto.findAll();

        if (all.isEmpty()){
            throw new EntityNotFoundException("Nenhum produto cadastrado");
        }

        return all.stream()
                .map(p ->
                    objectMapper.convertValue(p, ProdutoResponseDTO.class)
                )
                .toList();
    }

    public ProdutoResponseDTO getProdutoById(Integer id){
        Optional<Produto> byId = repositoryProduto.findById(id);

        if (byId.isEmpty()){
            throw new EntityNotFoundException("Produto com id " + id + " não encontrado");
        }

        return objectMapper.convertValue(byId.get(), ProdutoResponseDTO.class);
    }

    public ProdutoResponseDTO createProduto(ProdutoRequestDTO produto){
        Produto save = repositoryProduto.save(objectMapper.convertValue(produto, Produto.class));

        repositoryProduto.findById(save.getIdProduto())
                .orElseThrow(() -> new EntityNotFoundException("Produto não foi salvo no banco"));

        return objectMapper.convertValue(save, ProdutoResponseDTO.class);
    }

    public ProdutoResponseDTO updateProduto(Integer id, ProdutoRequestDTO produtoRequestDTO) {

        repositoryProduto.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com id " + id + " não encontrado."));
        try {
            Produto produtoParaSalvar = objectMapper.convertValue(produtoRequestDTO, Produto.class);
            produtoParaSalvar.setIdProduto(id);

            Produto produtoSalvo = repositoryProduto.save(produtoParaSalvar);
            return objectMapper.convertValue(produtoSalvo, ProdutoResponseDTO.class);
        } catch (DataIntegrityViolationException e) {
            String nomeProduto = produtoRequestDTO.getNomeProduto();
            if (e.getMessage() != null && e.getMessage().contains("produto_nome_produto_key")) {
                throw new IllegalArgumentException("O nome do produto '" + nomeProduto + "' já está em uso por outro registro.", e);
            }
            throw e;
        }
    }

    public ProdutoResponseDTO deleteProduto(Integer id){
        Produto produto = repositoryProduto.deletarProduto(id);

        if (produto == null){
            throw new EntityNotFoundException("Produto com id " + id + " não encontrado");
        }

        return objectMapper.convertValue(produto, ProdutoResponseDTO.class);
    }
}
