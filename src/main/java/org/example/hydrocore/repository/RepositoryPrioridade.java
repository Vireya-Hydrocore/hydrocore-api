package org.example.hydrocore.repository;

import org.example.hydrocore.repository.entity.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositoryPrioridade extends JpaRepository<Prioridade, Integer> {

    @Query(value = "SELECT * FROM prioridade WHERE nivel = :nivel", nativeQuery = true)
    Prioridade findByNivel(String nivel);
}
