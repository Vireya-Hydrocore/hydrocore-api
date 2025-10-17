package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.ProdutoDTO;
import org.example.hydrocore.dto.ProdutosUsadosMesDTO;
import org.example.hydrocore.dto.request.ProdutoRequestDTO;
import org.example.hydrocore.dto.response.ProdutoResponseDTO;
import org.example.hydrocore.dto.response.ProdutosUsadosMesResponseDTO;
import org.example.hydrocore.repository.RepositoryProduto;
import org.example.hydrocore.model.Produto;
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

    public List<ProdutoResponseDTO> getAllProdutos() {
        try {
            List<ProdutoDTO> all = repositoryProduto.listarProdutos();

            if (all.isEmpty()) {
                throw new EntityNotFoundException("Nenhum produto cadastrado");
            }

            return all.stream()
                    .map(p -> {
                        ProdutoResponseDTO dto = new ProdutoResponseDTO();
                        dto.setId(p.getIdProduto());
                        dto.setNomeProduto(p.getNomeProduto());
                        dto.setTipo(p.getTipo());
                        dto.setUnidadeMedida(p.getUnidadeMedida()); // já vem como String do SELECT
                        return dto;
                    })
                    .toList();

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produtos: " + e.getMessage(), e);
        }
    }

    public ProdutoResponseDTO getProdutoById(Integer id) {
        try {
            Optional<Produto> byId = repositoryProduto.findById(id);

            if (byId.isEmpty()) {
                throw new EntityNotFoundException("Produto com id " + id + " não encontrado");
            }

            Produto p = byId.get();
            ProdutoResponseDTO dto = new ProdutoResponseDTO();
            dto.setId(p.getIdProduto());
            dto.setNomeProduto(p.getNomeProduto());
            dto.setTipo(p.getTipo());
            dto.setUnidadeMedida(String.valueOf(p.getIdUnidadeMedida())); // converte ID em string
            return dto;

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produto por id: " + e.getMessage(), e);
        }
    }

    public ProdutoResponseDTO createProduto(ProdutoRequestDTO produto) {
        try {
            Produto save = repositoryProduto.save(objectMapper.convertValue(produto, Produto.class));

            repositoryProduto.findById(save.getIdProduto())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não foi salvo no banco"));

            ProdutoResponseDTO dto = new ProdutoResponseDTO();
            dto.setId(save.getIdProduto());
            dto.setNomeProduto(save.getNomeProduto());
            dto.setTipo(save.getTipo());
            dto.setUnidadeMedida(String.valueOf(save.getIdUnidadeMedida()));
            return dto;

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar produto: " + e.getMessage(), e);
        }
    }

    public ProdutoResponseDTO updateProduto(Integer id, ProdutoRequestDTO produtoRequestDTO) {
        try {
            repositoryProduto.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Produto com id " + id + " não encontrado."));

            Produto produtoParaSalvar = objectMapper.convertValue(produtoRequestDTO, Produto.class);
            produtoParaSalvar.setIdProduto(id);

            Produto produtoSalvo = repositoryProduto.save(produtoParaSalvar);

            ProdutoResponseDTO dto = new ProdutoResponseDTO();
            dto.setId(produtoSalvo.getIdProduto());
            dto.setNomeProduto(produtoSalvo.getNomeProduto());
            dto.setTipo(produtoSalvo.getTipo());
            dto.setUnidadeMedida(String.valueOf(produtoSalvo.getIdUnidadeMedida()));
            return dto;

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            String nomeProduto = produtoRequestDTO.getNomeProduto();
            if (e.getMessage() != null && e.getMessage().contains("produto_nome_produto_key")) {
                throw new IllegalArgumentException("O nome do produto '" + nomeProduto + "' já está em uso por outro registro.", e);
            }
            throw new RuntimeException("Erro de integridade ao atualizar produto: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    public ProdutoResponseDTO deleteProduto(Integer id) {
        try {
            Produto produto = repositoryProduto.deletarProduto(id);

            if (produto == null) {
                throw new EntityNotFoundException("Produto com id " + id + " não encontrado");
            }

            ProdutoResponseDTO dto = new ProdutoResponseDTO();
            dto.setId(produto.getIdProduto());
            dto.setNomeProduto(produto.getNomeProduto());
            dto.setTipo(produto.getTipo());
            dto.setUnidadeMedida(String.valueOf(produto.getIdUnidadeMedida()));
            return dto;

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage(), e);
        }
    }

    public List<ProdutosUsadosMesResponseDTO> getProdutosMaisUsadosMes(Integer mes, Integer ano){
            List<ProdutosUsadosMesDTO> produtos = repositoryProduto.listarProdutosUsadosMes(mes, ano);

            if (produtos.isEmpty()) {
                throw new EntityNotFoundException("Nenhum produto usado no mês " + mes + " de " + ano);
            }

            return produtos.stream().map(p -> objectMapper.convertValue(p, ProdutosUsadosMesResponseDTO.class)).toList();
    }

}
