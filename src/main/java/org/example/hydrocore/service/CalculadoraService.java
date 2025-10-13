package org.example.hydrocore.service;

import org.example.hydrocore.dto.request.CalculadoraCoagulacaoRequestDTO;
import org.example.hydrocore.dto.request.CalculadoraFloculacaoRequestDTO;
import org.example.hydrocore.dto.response.ProdutoDosagemResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraService {

    public ProdutoDosagemResponseDTO calcularFloculacao(CalculadoraFloculacaoRequestDTO request) {
        double volume = 1.0;
        double volumeLitros = volume * 1000;
        double dose = definirDoseBaseFloculante(request.getProdutoQuimico(), request.getPh(), request.getTurbidez());
        double concentracao = 1.0;
        double pureza = 0.9;
        double massaSeca = (dose * volumeLitros) / 1000;
        double quantidadeKg = (massaSeca / 1000) / (concentracao * pureza);
        return new ProdutoDosagemResponseDTO(request.getProdutoQuimico(), round(quantidadeKg));
    }

    public ProdutoDosagemResponseDTO calcularCoagulacao(CalculadoraCoagulacaoRequestDTO request) {
        double volumeLitros = request.getVolume() * 1000;
        double dose = definirDoseBaseCoagulante(
                request.getProdutoQuimico(),
                request.getPh(),
                request.getTurbidez(),
                request.getAlcalinidade()
        );
        double concentracao = 0.85;
        double pureza = 0.9;
        double massaSeca = (dose * volumeLitros) / 1000;
        double quantidadeKg = (massaSeca / 1000) / (concentracao * pureza);
        return new ProdutoDosagemResponseDTO(request.getProdutoQuimico(), round(quantidadeKg));
    }

    private double definirDoseBaseFloculante(String produto, double ph, int turbidez) {
        double base = 10.0;
        if (turbidez > 50) base += 5;
        if (ph < 6.5) base += 3;

        return switch (produto.toLowerCase()) {
            case "polímero" -> base * 0.8;
            case "sulfato de alumínio" -> base * 1.2;
            case "cal hidratada" -> base * 0.9;
            case "cloro" -> base * 1.1;
            default -> base;
        };
    }

    private double definirDoseBaseCoagulante(String produto, double ph, int turbidez, double alcalinidade) {
        double base = 12.0;
        if (turbidez > 50) base += 4;
        if (alcalinidade < 20) base += 3;
        if (ph < 6.5) base += 2;

        return switch (produto.toLowerCase()) {
            case "sulfato de alumínio" -> base * 1.1;
            case "cal hidratada" -> base * 0.9;
            case "polímero" -> base * 0.7;
            case "cloro" -> base * 1.2;
            default -> base;
        };
    }

    private double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
