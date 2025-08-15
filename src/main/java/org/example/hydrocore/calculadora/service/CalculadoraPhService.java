package org.example.hydrocore.calculadora.service;

import org.example.hydrocore.calculadora.dto.request.CalculoRequestDTO;
import org.example.hydrocore.calculadora.dto.response.CalculoResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculadoraPhService {

    // Fatores de conversão para cada produto (exemplo simplificado)
    private static final Map<String, Double> FATORES_PRODUTO = new HashMap<>();
    static {
        FATORES_PRODUTO.put("Polímero", 1.0);
        FATORES_PRODUTO.put("Sulfato de Alumínio", 1.2);
        FATORES_PRODUTO.put("Cal Hidratada", 0.8);
        FATORES_PRODUTO.put("Cloro", 0.5);
    }

    public List<CalculoResponseDTO> calcular(CalculoRequestDTO req) {
        List<CalculoResponseDTO> resultados = new ArrayList<>();
        double diferencaPh = Math.abs(req.getPhDesejado() - req.getPhAtual());

        for (String produto : req.getProdutos()) {
            double fator = FATORES_PRODUTO.getOrDefault(produto, 1.0);
            double dose = req.getVolumeM3() * fator * diferencaPh;

            if (req.getTurbidez() > 5) {
                dose *= 1.1;
            }

            resultados.add(new CalculoResponseDTO(
                    produto,
                    Math.round(dose * 100.0) / 100.0
            ));
        }

        return resultados;
    }

}
