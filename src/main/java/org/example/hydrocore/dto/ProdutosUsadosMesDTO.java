package org.example.hydrocore.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosUsadosMesDTO {

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "eta_nome")
    private String nomeEta;

    @Column(name = "total_usado")
    private BigDecimal totalUsado;

}
