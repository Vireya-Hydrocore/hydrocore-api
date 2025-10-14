package org.example.hydrocore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDTO {

    private Integer id;
    private String nomeProduto;
    private String tipo;
    private String unidadeMedida;

}
