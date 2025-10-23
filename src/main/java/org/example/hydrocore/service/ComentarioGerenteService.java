package org.example.hydrocore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.hydrocore.dto.request.ComentarioGerenteRequestDTO;
import org.example.hydrocore.dto.response.ComentarioGerenteResponseDTO;
import org.example.hydrocore.model.ComentarioGerente;
import org.example.hydrocore.projection.ComentarioGerenteProjection;
import org.example.hydrocore.repository.RepositoryComentarioGerente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ComentarioGerenteService {

    @Autowired
    private RepositoryComentarioGerente repositoryComentarioGerente;

    public List<ComentarioGerenteResponseDTO> listarComentariosGerente() {
        List<ComentarioGerenteProjection> comentarioGerenteProjections = repositoryComentarioGerente.listarComentariosGerente(null);

        if (comentarioGerenteProjections.isEmpty()) {
            throw new EntityNotFoundException("Nenhum comentário encontrado.");
        }

        return comentarioGerenteProjections.stream().map(this::getComentarioGerenteResponseDTO).toList();
    }

    public ComentarioGerenteResponseDTO buscarComentarioGerentePorId(Integer id) {
        List<ComentarioGerenteProjection> comentarioGerenteProjections = repositoryComentarioGerente.listarComentariosGerente(id);

        if (comentarioGerenteProjections.isEmpty()){
            throw new EntityNotFoundException("Comentário não encontrado.");
        }

        ComentarioGerenteProjection projection = comentarioGerenteProjections.get(0);

        return getComentarioGerenteResponseDTO(projection);
    }

    public ComentarioGerenteResponseDTO salvarComentarioGerente(ComentarioGerenteRequestDTO comentarioGerenteRequestDTO) {
        LocalDate dataDoComentario = comentarioGerenteRequestDTO.getAnoMes();
        LocalDate dataAtual = LocalDate.now();

        boolean mesmoMesEAno = dataDoComentario.getYear() == dataAtual.getYear()
                && dataDoComentario.getMonth() == dataAtual.getMonth();

        if (mesmoMesEAno) {
            boolean jaExiste = repositoryComentarioGerente.existsByAnoMesAndIdEtaAdmin(comentarioGerenteRequestDTO.getAnoMes(), comentarioGerenteRequestDTO.getIdEtaAdmin());

            if (jaExiste) {
                throw new IllegalStateException("Já existe um comentário para este mês. Atualize o comentário existente em vez de adicionar um novo.");
            }
        }

        ComentarioGerente comentarioGerente = new ComentarioGerente();
        comentarioGerente.setComentario(comentarioGerenteRequestDTO.getComentario());
        comentarioGerente.setAnoMes(comentarioGerenteRequestDTO.getAnoMes());
        comentarioGerente.setIdEtaAdmin(comentarioGerenteRequestDTO.getIdEtaAdmin());

        ComentarioGerente salvo = repositoryComentarioGerente.save(comentarioGerente);

        return buscarComentarioGerentePorId(salvo.getId());
    }


    public ComentarioGerenteResponseDTO atualizarComentarioGerente(Integer id, ComentarioGerenteRequestDTO comentarioGerenteRequestDTO) {
        ComentarioGerente comentarioGerente = repositoryComentarioGerente.findById(id).orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado."));

        comentarioGerente.setComentario(comentarioGerenteRequestDTO.getComentario());
        comentarioGerente.setAnoMes(comentarioGerenteRequestDTO.getAnoMes());
        comentarioGerente.setIdEtaAdmin(comentarioGerenteRequestDTO.getIdEtaAdmin());

        ComentarioGerente comentarioGerenteSalvo = repositoryComentarioGerente.save(comentarioGerente);

        ComentarioGerenteResponseDTO comentarioGerenteResponseDTO = buscarComentarioGerentePorId(comentarioGerenteSalvo.getId());

        if (comentarioGerenteResponseDTO == null) {
            throw new EntityNotFoundException("Erro ao atualizar comentário.");
        }

        return comentarioGerenteResponseDTO;
    }

    public ComentarioGerenteResponseDTO removerComentarioGerente(Integer id) {
        ComentarioGerenteResponseDTO comentarioGerenteResponseDTO = buscarComentarioGerentePorId(id);
        repositoryComentarioGerente.deleteById(id);
        return comentarioGerenteResponseDTO;
    }


    private ComentarioGerenteResponseDTO getComentarioGerenteResponseDTO(ComentarioGerenteProjection p) {
        ComentarioGerenteResponseDTO comentarioGerenteResponseDTO = new ComentarioGerenteResponseDTO();
        comentarioGerenteResponseDTO.setId(p.getId());
        comentarioGerenteResponseDTO.setComentario(p.getComentario());
        comentarioGerenteResponseDTO.setAnoMes(p.getAnoMes());
        comentarioGerenteResponseDTO.setNomeEtaAdmin(p.getNomeEtaAdmin());
        comentarioGerenteResponseDTO.setIdEtaAdmin(p.getIdEtaAdmin());
        return comentarioGerenteResponseDTO;
    }

}
