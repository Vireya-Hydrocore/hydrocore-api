package org.example.hydrocore.repository;


import org.example.hydrocore.model.ComentarioGerente;
import org.example.hydrocore.projection.ComentarioGerenteProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryComentarioGerente extends JpaRepository<ComentarioGerente, Integer> {

    @Query(value = """
       SELECT
           c.id_comentario AS id,
           c.comentario,
           c.ano_mes AS anoMes,
           et.nome AS nomeEtaAdmin,
           e.id_eta_admin AS idEtaAdmin
       FROM comentario_gerente c
       JOIN eta_admin e on e.id_eta_admin = c.id_eta_admin
       JOIN eta et ON e.id_eta = et.id_eta
       WHERE (:idComentario IS NULL OR c.id_comentario = :idComentario);
    """, nativeQuery = true)
    List<ComentarioGerenteProjection> listarComentariosGerente(Integer idComentario);

    boolean existsByAnoMesAndIdEtaAdmin(LocalDate anoMes, Integer idEtaAdmin);

}
