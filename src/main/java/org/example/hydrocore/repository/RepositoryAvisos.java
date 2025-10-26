package org.example.hydrocore.repository;

import org.example.hydrocore.model.Avisos;
import org.example.hydrocore.projection.AvisosHojeProjection;
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
        LEFT JOIN eta e on e.id_eta = a.id_eta
        LEFT JOIN prioridade p on p.id_prioridade = a.id_prioridade
        LEFT JOIN status s on a.id_status = s.id_status
        WHERE (:idAviso IS NULL OR a.id_avisos = :idAviso);
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
        WHERE a.data_ocorrencia BETWEEN (CAST(:dataReferencia AS DATE) - INTERVAL '6 days') AND :dataReferencia;
        """, nativeQuery = true)
    List<AvisosProjection> getAvisosByData(@Param("dataReferencia") LocalDate dataReferencia);

    @Query(value = """
        SELECT
            a.id_avisos AS id,
            f.nome AS nome,
            a.descricao,
            t.data_criacao AS dataCriacao,
            s.status AS status
        FROM avisos a
        LEFT JOIN eta e on e.id_eta = a.id_eta
        LEFT JOIN status s on a.id_status = s.id_status
        LEFT JOIN funcionario f on e.id_eta = f.id_eta
        LEFT JOIN tarefa t on f.id_funcionario = t.id_funcionario
        WHERE t.data_criacao = CURRENT_DATE
    """, nativeQuery = true)
    List<AvisosHojeProjection> getAvisosHoje();

}
