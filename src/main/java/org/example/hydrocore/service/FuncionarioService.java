package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.request.FuncionarioRequestDTO;
import org.example.hydrocore.dto.response.FuncionarioResponseDTO;
import org.example.hydrocore.repository.RepositoryCargo;
import org.example.hydrocore.repository.RepositoryEstacaoTratamentoDaAgua;
import org.example.hydrocore.repository.RepositoryFuncionario;
import org.example.hydrocore.repository.entity.Cargo;
import org.example.hydrocore.repository.entity.EstacaoTratamentoDaAgua;
import org.example.hydrocore.repository.entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private final RepositoryFuncionario funcionarioRepository;

    @Autowired
    private final RepositoryEstacaoTratamentoDaAgua etaRepository;

    @Autowired
    private final RepositoryCargo repositoryCargo;

    @Autowired
    private ObjectMapper objectMapper;

    public FuncionarioService(RepositoryFuncionario funcionarioRepository, RepositoryEstacaoTratamentoDaAgua etaRepository, RepositoryCargo repositoryCargo) {
        this.funcionarioRepository = funcionarioRepository;
        this.etaRepository = etaRepository;
        this.repositoryCargo = repositoryCargo;
    }

    public List<FuncionarioResponseDTO> getAllFuncionarios() {
        List<Funcionario> all = funcionarioRepository.listarFuncionarios();

        if (all.isEmpty()) {
            return Collections.emptyList();
        }

        return all.stream()
                .map(m -> objectMapper.convertValue(m, FuncionarioResponseDTO.class))
                .toList();
    }

    public FuncionarioResponseDTO getFuncionarioById(Integer idFuncionario) {
        try{
            Optional<Funcionario> byId = funcionarioRepository.findById(idFuncionario);
            return objectMapper.convertValue(byId.get(), FuncionarioResponseDTO.class);

        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw new EntityNotFoundException("Erro: " + ex.getMessage());
        }
    }

    public FuncionarioResponseDTO deletarFuncionario(Integer idFuncionario) {
        try{
            Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                    .orElseThrow(() -> new EntityNotFoundException("Funcionario não encontrado"));

            Funcionario funcionario1 = funcionarioRepository.deletarFuncionario(idFuncionario);

            return  objectMapper.convertValue(funcionario, FuncionarioResponseDTO.class);
        } catch (CannotCreateTransactionException ex){
            ex.printStackTrace();
            throw new CannotCreateTransactionException("Erro: " + ex.getMessage());
        }
    }

    public FuncionarioResponseDTO salvarFuncionario(FuncionarioRequestDTO requestDTO) {
        EstacaoTratamentoDaAgua eta = etaRepository.findById(requestDTO.getIdEta())
                .orElseThrow(() -> new EntityNotFoundException("ETA não encontrada com id " + requestDTO.getIdEta()));

        Cargo cargo = repositoryCargo.findById(requestDTO.getIdCargo())
                .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado com id " + requestDTO.getIdCargo()));

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(requestDTO.getNome());
        funcionario.setEmail(requestDTO.getEmail());
        funcionario.setDataAdmissao(requestDTO.getDataAdmissao());
        funcionario.setDataNascimento(requestDTO.getDataNascimento());
        funcionario.setIdEta(eta);
        funcionario.setIdCargo(cargo);

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        return new FuncionarioResponseDTO(
                funcionarioSalvo.getIdFuncionario(),
                funcionarioSalvo.getNome(),
                funcionarioSalvo.getEmail(),
                funcionarioSalvo.getDataAdmissao(),
                funcionarioSalvo.getDataNascimento(),
                funcionarioSalvo.getIdEta().getNome(),
                funcionarioSalvo.getIdCargo().getNome(),
                funcionarioSalvo.getTarefas() != null && !funcionarioSalvo.getTarefas().isEmpty()
                        ? funcionarioSalvo.getTarefas().get(0).getDescricao() : null,
                funcionarioSalvo.getTarefas() != null && !funcionarioSalvo.getTarefas().isEmpty()
                        ? funcionarioSalvo.getTarefas().get(0).getStatus() : null
        );
    }

    public FuncionarioResponseDTO atualizarFuncionario(Integer id, FuncionarioRequestDTO requestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com id " + id));

        EstacaoTratamentoDaAgua eta = etaRepository.findById(requestDTO.getIdEta())
                .orElseThrow(() -> new EntityNotFoundException("ETA não encontrada com id " + requestDTO.getIdEta()));

        Cargo cargo = repositoryCargo.findById(requestDTO.getIdCargo())
                .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado com id " + requestDTO.getIdCargo()));

        funcionario.setNome(requestDTO.getNome());
        funcionario.setEmail(requestDTO.getEmail());
        funcionario.setDataAdmissao(requestDTO.getDataAdmissao());
        funcionario.setDataNascimento(requestDTO.getDataNascimento());
        funcionario.setIdEta(eta);
        funcionario.setIdCargo(cargo);

        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionario);

        return new FuncionarioResponseDTO(
                funcionarioAtualizado.getIdFuncionario(),
                funcionarioAtualizado.getNome(),
                funcionarioAtualizado.getEmail(),
                funcionarioAtualizado.getDataAdmissao(),
                funcionarioAtualizado.getDataNascimento(),
                funcionarioAtualizado.getIdEta().getNome(),
                funcionarioAtualizado.getIdCargo().getNome(),
                funcionarioAtualizado.getTarefas() != null && !funcionarioAtualizado.getTarefas().isEmpty()
                        ? funcionarioAtualizado.getTarefas().get(0).getDescricao() : null,
                funcionarioAtualizado.getTarefas() != null && !funcionarioAtualizado.getTarefas().isEmpty()
                        ? funcionarioAtualizado.getTarefas().get(0).getStatus() : null
        );
    }



}