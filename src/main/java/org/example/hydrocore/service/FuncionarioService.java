package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.ResumoTarefasDTO;
import org.example.hydrocore.dto.ResumoTarefasResponseDTO;
import org.example.hydrocore.dto.request.FuncionarioPatchRequestDTO;
import org.example.hydrocore.dto.request.FuncionarioRequestDTO;
import org.example.hydrocore.dto.response.FuncionarioIdResponseDTO;
import org.example.hydrocore.dto.response.FuncionarioResponseDTO;
import org.example.hydrocore.projection.FuncionarioDTO;
import org.example.hydrocore.repository.RepositoryCargo;
import org.example.hydrocore.repository.RepositoryEstacaoTratamentoDaAgua;
import org.example.hydrocore.repository.RepositoryFuncionario;
import org.example.hydrocore.repository.entity.Cargo;
import org.example.hydrocore.repository.entity.EstacaoTratamentoDaAgua;
import org.example.hydrocore.repository.entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.ArrayList;
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
        List<FuncionarioDTO> funcionariosProjecao = funcionarioRepository.listarFuncionarios(null);

        if (funcionariosProjecao.isEmpty()) {
            throw new EntityNotFoundException("Nenhum Funcionario encontrado");
        }

        return funcionariosProjecao.stream()
                .map(projecao -> new FuncionarioResponseDTO(
                        projecao.getId(),
                        projecao.getNome(),
                        projecao.getEmail(),
                        projecao.getDataAdmissao(),
                        projecao.getDataNascimento(),
                        projecao.getEta(),
                        projecao.getCargo()
                ))
                .toList();
    }

    public FuncionarioResponseDTO getFuncionarioById(Integer idFuncionario) {
        try{
            List<FuncionarioDTO> byId = funcionarioRepository.listarFuncionarios(idFuncionario);
            return objectMapper.convertValue(byId.get(0), FuncionarioResponseDTO.class);

        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw new EntityNotFoundException("Erro: " + ex.getMessage());
        }
    }

    public FuncionarioResponseDTO deletarFuncionario(Integer idFuncionario) {
        try{
            Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                    .orElseThrow(() -> new EntityNotFoundException("Funcionario não encontrado"));

            funcionarioRepository.deletarFuncionario(idFuncionario);

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
        funcionario.setSenha(requestDTO.getSenha());
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
                funcionarioSalvo.getIdCargo().getNome()
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
                funcionarioAtualizado.getIdCargo().getNome()
        );
    }

    public FuncionarioIdResponseDTO mostrarFuncionarioPorEmail(String email){
        Integer idFuncionario = funcionarioRepository.getIdFuncionario(email);

        if (idFuncionario == null) {
            throw new EntityNotFoundException("Funcionário com o e-mail '" + email + "' não foi encontrado.");
        }

        FuncionarioIdResponseDTO responseDTO = new FuncionarioIdResponseDTO();
        responseDTO.setIdFuncionario(idFuncionario);

        return responseDTO;
    }

    public FuncionarioResponseDTO atualizarParcialFuncionario(Integer id, FuncionarioPatchRequestDTO requestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com id " + id));

        if (requestDTO.getNome() != null) {
            funcionario.setNome(requestDTO.getNome());
        }
        if (requestDTO.getEmail() != null) {
            funcionario.setEmail(requestDTO.getEmail());
        }
        if (requestDTO.getDataAdmissao() != null) {
            funcionario.setDataAdmissao(requestDTO.getDataAdmissao());
        }
        if (requestDTO.getDataNascimento() != null) {
            funcionario.setDataNascimento(requestDTO.getDataNascimento());
        }
        if (requestDTO.getIdEta() != null) {
            EstacaoTratamentoDaAgua eta = etaRepository.findById(requestDTO.getIdEta())
                    .orElseThrow(() -> new EntityNotFoundException("ETA não encontrada com id " + requestDTO.getIdEta()));
            funcionario.setIdEta(eta);
        }
        if (requestDTO.getIdCargo() != null) {
            Cargo cargo = repositoryCargo.findById(requestDTO.getIdCargo())
                    .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado com id " + requestDTO.getIdCargo()));
            funcionario.setIdCargo(cargo);
        }

        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionario);

        return new FuncionarioResponseDTO(
                funcionarioAtualizado.getIdFuncionario(),
                funcionarioAtualizado.getNome(),
                funcionarioAtualizado.getEmail(),
                funcionarioAtualizado.getDataAdmissao(),
                funcionarioAtualizado.getDataNascimento(),
                funcionarioAtualizado.getIdEta().getNome(),
                funcionarioAtualizado.getIdCargo().getNome()
        );
    }

    public ResumoTarefasResponseDTO resumoTarefasUsuario(Integer idFuncionario){
        ResumoTarefasDTO resumo = funcionarioRepository.resumoTarefasUsuario(idFuncionario);

        if (resumo == null) {
            throw new EntityNotFoundException("Nenhuma tarefa encontrada para o usuário com id " + idFuncionario);
        }

        return objectMapper.convertValue(resumo, ResumoTarefasResponseDTO.class);

    }

}