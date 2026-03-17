package com.automotive.repository;

import com.automotive.model.RendezVous;
import com.automotive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByUser(User user);

    List<RendezVous> findByStatut(RendezVous.Statut statut);

    long countByStatut(RendezVous.Statut statut);

    @Query("SELECT r FROM RendezVous r WHERE r.dateRdv BETWEEN :dateDebut AND :dateFin")
    List<RendezVous> findByDateRange(@Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin);

    @Query("SELECT r FROM RendezVous r WHERE r.user.id = :userId ORDER BY r.dateRdv DESC")
    List<RendezVous> findByUserIdOrderByDateDesc(@Param("userId") Long userId);
}
