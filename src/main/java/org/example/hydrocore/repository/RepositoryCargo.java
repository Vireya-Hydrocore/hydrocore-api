package org.example.hydrocore.repository;

import org.example.hydrocore.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryCargo extends JpaRepository<Cargo, Integer> {

}