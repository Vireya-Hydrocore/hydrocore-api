package org.example.hydrocore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCriarResponseDTO {

    private Integer id;
    private String nome;
    private String tipo;
    private Integer idUnidadeMedida;

}
