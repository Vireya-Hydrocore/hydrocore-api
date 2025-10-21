package org.example.hydrocore.repository;

import org.example.hydrocore.model.Avisos;
import org.example.hydrocore.projection.AvisosProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryAvisos extends JpaRepository<Avisos, Integer> {

    @Query(value = "DELETE FROM avisos WHERE id_avisos = :idAvisos RETURNING *;", nativeQuery = true)
    Avisos deleteByIdAvisos(Integer idAvisos);

    @Query(value = """
        SELECT
            a.id_avisos AS id,
            a.descricao,
            a.data_ocorrencia AS dataOcorrencia,
            e.nome AS nomeEta,
            p.nivel AS prioridade,
            s.status AS status
        FROM avisos a
        JOIN eta e on e.id_eta = a.id_eta
        JOIN prioridade p on p.id_prioridade = a.id_prioridade
        JOIN status s on a.id_status = s.id_status
        WHERE (:idAviso IS NULL OR a.id_aviso = :idAviso);
    """, nativeQuery = true)
    List<AvisosProjection> getAllAvisos(@Param("idAviso") Integer idAvisos);

    @Query(value = """
        SELECT
            a.id_avisos AS id,
            a.descricao,
            a.data_ocorrencia AS dataOcorrencia,
            e.nome AS nomeEta,
            p.nivel AS prioridade,
            s.status AS status
        FROM avisos a
        JOIN eta e ON e.id_eta = a.id_eta
        JOIN prioridade p ON p.id_prioridade = a.id_prioridade
        JOIN status s ON a.id_status = s.id_status
        WHERE a.data_ocorrencia BETWEEN (:dataReferencia - INTERVAL '6 days') AND :dataReferencia           
    """, nativeQuery = true)
    List<AvisosProjection> getAvisosByData(@Param("dataReferencia") LocalDate dataReferencia);

}
