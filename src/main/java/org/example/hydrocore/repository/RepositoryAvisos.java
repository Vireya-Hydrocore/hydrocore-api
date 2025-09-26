package org.example.hydrocore.repository;

import org.example.hydrocore.repository.entity.Avisos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryAvisos extends JpaRepository<Avisos, Long> {

    List<Avisos> findAllByIdEta(Long idEta);

}
