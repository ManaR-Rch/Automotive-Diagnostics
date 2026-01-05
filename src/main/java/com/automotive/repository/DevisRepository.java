package com.automotive.repository;

import com.automotive.model.Devis;
import com.automotive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {
    List<Devis> findByUser(User user);

    List<Devis> findByStatut(Devis.Statut statut);

    @Query("SELECT d FROM Devis d WHERE d.user.id = :userId ORDER BY d.dateDemande DESC")
    List<Devis> findByUserIdOrderByDateDesc(@Param("userId") Long userId);

    @Query("SELECT COUNT(d) FROM Devis d WHERE d.statut = 'EN_ATTENTE'")
    long countByStatutEnAttente();
}
