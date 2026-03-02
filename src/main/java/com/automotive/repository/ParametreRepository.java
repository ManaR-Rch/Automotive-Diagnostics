package com.automotive.repository;

import com.automotive.model.Parametre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParametreRepository extends JpaRepository<Parametre, Long> {
    Optional<Parametre> findByCleTechnique(String cleTechnique);
}
