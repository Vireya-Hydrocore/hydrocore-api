package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.hydrocore.dto.response.CargoResponseDTO;
import org.example.hydrocore.model.Cargo;
import org.example.hydrocore.repository.RepositoryCargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CargoService {

    @Autowired
    private RepositoryCargo cargoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<CargoResponseDTO> listarCargos() {
        return cargoRepository.findAll().stream()
                .map(cargo -> objectMapper.convertValue(cargo, CargoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public CargoResponseDTO buscarCargoPorId(Integer id) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cargo com ID " + id + " não encontrado."));

        return objectMapper.convertValue(cargo, CargoResponseDTO.class);
    }

}