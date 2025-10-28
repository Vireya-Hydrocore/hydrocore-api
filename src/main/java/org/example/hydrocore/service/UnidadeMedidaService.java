package org.example.hydrocore.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.response.UnidadeMedidaResponseDTO;
import org.example.hydrocore.model.UnidadeMedida;
import org.example.hydrocore.repository.RepositoryUnidadeMedida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadeMedidaService {

    @Autowired
    private RepositoryUnidadeMedida repositoryUnidadeMedida;

    public List<UnidadeMedidaResponseDTO> mostrarUnidadeMedida() {
        List<UnidadeMedida> unidades = repositoryUnidadeMedida.findAll();

        if (unidades.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma unidade de medida encontrada.");
        }

        // Conversão manual do modelo para o DTO
        return unidades.stream()
                .map(u -> new UnidadeMedidaResponseDTO(u.getId(), u.getNome()))
                .collect(Collectors.toList());
    }
}
