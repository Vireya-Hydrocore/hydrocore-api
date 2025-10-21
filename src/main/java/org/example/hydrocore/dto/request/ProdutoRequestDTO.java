package org.example.hydrocore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequestDTO {

    @NotBlank
    @NotNull
    private String nomeProduto;

    @NotBlank
    @NotNull
    private String tipo;

    @NotNull
    private Integer idUnidadeMedida;


}
