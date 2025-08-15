package org.example.hydrocore.calculadora.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculoResponseDTO {

    private String produto;
    private double quantidadeKg;


}
