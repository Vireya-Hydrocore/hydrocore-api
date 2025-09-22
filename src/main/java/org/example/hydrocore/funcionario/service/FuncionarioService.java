package org.example.hydrocore.funcionario.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hydrocore.funcionario.dto.FuncionarioRelatorioDTO;
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

    public List<FuncionarioRelatorioDTO> getAllFuncionarios() {
        List<FuncionarioRelatorioDTO> all = funcionarioRepository.listarFuncionarios();

        if (all.isEmpty()) {
            return Collections.emptyList();
        }

        return all;
    }

//    private FuncionarioResponseDTO deleteFuncionarioById(Long id) {
//
//    }



}