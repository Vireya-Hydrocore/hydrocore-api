package org.example.hydrocore.estoque.dto;

public record EstoqueDTO (
        Long idEstoque,
        Integer quantidade,
        String nomeProduto,
        String nomeEta
) {}
