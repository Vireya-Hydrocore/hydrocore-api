package org.example.hydrocore.repository;

import org.example.hydrocore.repository.entity.Avisos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryAvisos extends JpaRepository<Avisos, Integer> {

    List<Avisos> findAllByIdEta(Integer idEta);

    @Query(value = "DELETE FROM avisos WHERE id_avisos = :idAvisos RETURNING *;", nativeQuery = true)
    Avisos deleteByIdAvisos(Integer idAvisos);

}
