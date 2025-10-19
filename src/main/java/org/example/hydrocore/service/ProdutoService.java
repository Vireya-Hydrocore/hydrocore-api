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
                    dto.setUnidadeMedida(p.getUnidadeMedida());
                    return dto;
                })
                .toList();
    }

    public ProdutoResponseDTO getProdutoById(Integer id) {
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
    }

    public ProdutoResponseDTO createProduto(ProdutoRequestDTO produto) {
            Produto save = repositoryProduto.save(objectMapper.convertValue(produto, Produto.class));

            repositoryProduto.findById(save.getIdProduto())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não foi salvo no banco"));

            ProdutoResponseDTO dto = new ProdutoResponseDTO();
            dto.setId(save.getIdProduto());
            dto.setNomeProduto(save.getNomeProduto());
            dto.setTipo(save.getTipo());
            dto.setUnidadeMedida(String.valueOf(save.getIdUnidadeMedida()));
            return dto;
    }

    public ProdutoResponseDTO updateProduto(Integer id, ProdutoRequestDTO produtoRequestDTO) {
        repositoryProduto.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com id " + id + " não encontrado."));

        Produto produtoParaSalvar = objectMapper.convertValue(produtoRequestDTO, Produto.class);
        produtoParaSalvar.setIdProduto(id);

        String unidadeStr = produtoRequestDTO.getUnidadeMedida().trim().toLowerCase();
        Integer idUnidade = switch (unidadeStr) {
            case "kilos" -> 1;
            case "litro" -> 2;
            case "unidade" -> 3;
            default -> throw new IllegalArgumentException(
                    "Unidade '" + unidadeStr + "' inválida. Use: kilos, litro ou unidade.");
        };
        produtoParaSalvar.setIdUnidadeMedida(idUnidade);

        Produto produtoSalvo = repositoryProduto.save(produtoParaSalvar);

        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produtoSalvo.getIdProduto());
        dto.setNomeProduto(produtoSalvo.getNomeProduto());
        dto.setTipo(produtoSalvo.getTipo());
        dto.setUnidadeMedida(produtoRequestDTO.getUnidadeMedida());

        return dto;
    }



    public ProdutoResponseDTO deleteProduto(Integer id) {
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
    }

    public List<ProdutosUsadosMesResponseDTO> getProdutosMaisUsadosMes(Integer mes, Integer ano){
            List<ProdutosUsadosMesDTO> produtos = repositoryProduto.listarProdutosUsadosMes(mes, ano);

            if (produtos.isEmpty()) {
                throw new EntityNotFoundException("Nenhum produto usado no mês " + mes + " de " + ano);
            }

            return produtos.stream().map(p -> objectMapper.convertValue(p, ProdutosUsadosMesResponseDTO.class)).toList();
    }

}
