package com.automotive.repository;

import com.automotive.model.Creneau;
import com.automotive.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CreneauRepository extends JpaRepository<Creneau, Long> {
    List<Creneau> findByRendezVous(RendezVous rendezVous);

    List<Creneau> findByDisponibleTrue();

    List<Creneau> findByDateCreneauBetween(LocalDateTime start, LocalDateTime end);
}
