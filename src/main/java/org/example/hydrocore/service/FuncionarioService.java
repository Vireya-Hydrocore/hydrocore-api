package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.FuncionarioEmailDTO;
import org.example.hydrocore.dto.ResumoTarefasDTO;
import org.example.hydrocore.dto.request.FuncionarioPatchRequestDTO;
import org.example.hydrocore.dto.request.FuncionarioRequestDTO;
import org.example.hydrocore.dto.response.FuncionarioEmailResponseDTO;
import org.example.hydrocore.dto.response.FuncionarioResponseDTO;
import org.example.hydrocore.dto.response.OrganogramaResponseDTO;
import org.example.hydrocore.dto.response.ResumoTarefasResponseDTO;
import org.example.hydrocore.model.Cargo;
import org.example.hydrocore.model.EstacaoTratamentoDaAgua;
import org.example.hydrocore.model.Funcionario;
import org.example.hydrocore.projection.FuncionarioProjection;
import org.example.hydrocore.projection.OrganogramaProjection;
import org.example.hydrocore.repository.RepositoryCargo;
import org.example.hydrocore.repository.RepositoryEstacaoTratamentoDaAgua;
import org.example.hydrocore.repository.RepositoryFuncionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final RepositoryFuncionario funcionarioRepository;

    private final RepositoryEstacaoTratamentoDaAgua etaRepository;

    private final RepositoryCargo repositoryCargo;

    @Autowired
    private ObjectMapper objectMapper;

    public FuncionarioService(RepositoryFuncionario funcionarioRepository, RepositoryEstacaoTratamentoDaAgua etaRepository, RepositoryCargo repositoryCargo) {
        this.funcionarioRepository = funcionarioRepository;
        this.etaRepository = etaRepository;
        this.repositoryCargo = repositoryCargo;
    }

    public List<FuncionarioResponseDTO> getAllFuncionarios() {
        List<FuncionarioProjection> funcionariosProjecao = funcionarioRepository.listarFuncionarios(null);

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
                        projecao.getCargo(),
                        projecao.getIdEta()
                ))
                .toList();
    }

    public FuncionarioResponseDTO getFuncionarioById(Integer idFuncionario) {
            List<FuncionarioProjection> byId = funcionarioRepository.listarFuncionarios(idFuncionario);

            if (byId.isEmpty()) {
                throw new EntityNotFoundException("Nenhum Funcionario encontrado");
            }

        return objectMapper.convertValue(byId.get(0), FuncionarioResponseDTO.class);

    }

    public FuncionarioResponseDTO deletarFuncionario(Integer idFuncionario) {
            Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                    .orElseThrow(() -> new EntityNotFoundException("Funcionario não encontrado"));

            funcionarioRepository.deletarFuncionario(idFuncionario);

            return  objectMapper.convertValue(funcionario, FuncionarioResponseDTO.class);
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
                funcionarioSalvo.getIdFuncionario()
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
                eta.getIdEta()
        );
    }

    public FuncionarioEmailResponseDTO mostrarFuncionarioPorEmail(String email){
        FuncionarioEmailDTO funcionarioEmailDTO = funcionarioRepository.getFuncionarioByEmail(email);

        if (funcionarioEmailDTO == null) {
            throw new EntityNotFoundException("Funcionário com o e-mail '" + email + "' não foi encontrado.");
        }

        FuncionarioEmailResponseDTO funcionarioEmailResponseDTO = new FuncionarioEmailResponseDTO();

        funcionarioEmailResponseDTO.setNome(funcionarioEmailDTO.getNome());
        funcionarioEmailResponseDTO.setCargo(funcionarioEmailDTO.getCargo());
        funcionarioEmailResponseDTO.setId(funcionarioEmailDTO.getId());

        return funcionarioEmailResponseDTO;

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
                funcionarioAtualizado.getIdCargo().getNome(),
                funcionario.getIdEta().getIdEta()
        );
    }

    public ResumoTarefasResponseDTO resumoTarefasUsuario(Integer idFuncionario){
        ResumoTarefasDTO resumo = funcionarioRepository.resumoTarefasUsuario(idFuncionario);

        if (resumo == null) {
            throw new EntityNotFoundException("Nenhuma tarefa encontrada para o usuário com id " + idFuncionario);
        }

        return objectMapper.convertValue(resumo, ResumoTarefasResponseDTO.class);

    }

    public List<OrganogramaResponseDTO> listarOrganogramas(Integer idEta){

        List<OrganogramaProjection> organogramaProjections = funcionarioRepository.organogramaEta(idEta);

        if (organogramaProjections.isEmpty()){
            throw new EntityNotFoundException("Não existem organogramas para a ETA com id " + idEta);
        }

        return organogramaProjections.stream().map(p -> {
            OrganogramaResponseDTO org = new OrganogramaResponseDTO();

            org.setId(p.getIdFuncionario());
            org.setCargo(p.getCargoFuncionario());
            org.setNome(p.getNomeFuncionario());
            org.setIdSupervisor(p.getIdSupervisor());
            org.setNomeSupervisor(p.getNomeSupervisor());

            return org;
        }).toList();

    }

}