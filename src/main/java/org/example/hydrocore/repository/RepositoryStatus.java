package org.example.hydrocore.repository;

import org.example.hydrocore.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositoryStatus extends JpaRepository<Status, Integer> {

    @Query(value = "SELECT * FROM status s WHERE s.status = :status", nativeQuery = true)
    Status findByStatus(@Param("status") String status);
}
