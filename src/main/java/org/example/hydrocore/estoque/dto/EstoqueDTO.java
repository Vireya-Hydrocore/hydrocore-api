package org.example.hydrocore.estoque.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDTO {

    private Long idEstoque;

    private Integer quantidade;

    private String nomeProduto;

    private String nomeEta;
}

