package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hydrocore.dto.FuncionarioFunctionDTO;
import org.example.hydrocore.repository.RepositoryFuncionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private RepositoryFuncionario funcionarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<FuncionarioFunctionDTO> getAllFuncionarios() {
        List<FuncionarioFunctionDTO> all = funcionarioRepository.listarFuncionarios();

        if (all.isEmpty()) {
            return Collections.emptyList();
        }

        return all.stream()
                .map(m -> objectMapper.convertValue(m, FuncionarioFunctionDTO.class))
                .toList();
    }


}