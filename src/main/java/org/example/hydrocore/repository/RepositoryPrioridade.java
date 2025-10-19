package org.example.hydrocore.repository;

import org.example.hydrocore.model.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositoryPrioridade extends JpaRepository<Prioridade, Integer> {

    @Query("SELECT p FROM prioridade p WHERE LOWER(p.nivel) = LOWER(:nivel)")
    Prioridade findByNivelCaseInsensitive(@Param("nivel") String nivel);
}
