package com.automotive.repository;

import com.automotive.model.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {
    Optional<Marque> findByNom(String nom);

    List<Marque> findByActifTrueOrderByOrdreAffichageAsc();

    List<Marque> findByEstPartenaireTrue();
}
