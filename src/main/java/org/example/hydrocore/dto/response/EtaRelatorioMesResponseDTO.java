package org.example.hydrocore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtaRelatorioMesResponseDTO {

    private Integer id;
    private BigInteger volumeTratado;
    private String nome;
    private String nomeAdmin;
    private String cidade;
    private String estado;
    private String bairro;
    private Double phMin;
    private Double phMax;

}
