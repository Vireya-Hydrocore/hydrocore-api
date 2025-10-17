package org.example.hydrocore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDTO {

    private Integer id;

    private Integer quantidade;

    private String nomeProduto;

    private String nomeEta;
}

