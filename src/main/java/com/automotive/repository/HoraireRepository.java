package com.automotive.repository;

import com.automotive.model.Horaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HoraireRepository extends JpaRepository<Horaire, Long> {
    Optional<Horaire> findByJourSemaine(Horaire.JourSemaine jour);
}
