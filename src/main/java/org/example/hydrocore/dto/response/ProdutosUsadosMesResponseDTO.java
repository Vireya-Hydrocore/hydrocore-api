package org.example.hydrocore.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutosUsadosMesResponseDTO {

    private String nomeProduto;
    private String nomeEta;
    private BigDecimal totalUsado;

}
