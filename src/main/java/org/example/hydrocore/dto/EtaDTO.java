package org.example.hydrocore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtaDTO {

    private Integer id;
    private String nome;
    private String telefone;
    private BigDecimal capacidadeTratamento;
    private String cidade;

}
