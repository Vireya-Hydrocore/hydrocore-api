package org.example.hydrocore.calculadora.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculoRequestDTO {
    private String tipoAgua;
    private List<String> produtos;
    private double volumeM3;
    private double phAtual;
    private double phDesejado;
    private double turbidez;
}
