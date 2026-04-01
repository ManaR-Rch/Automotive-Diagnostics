package com.automotive.repository;

import com.automotive.model.Service;
import com.automotive.enums.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByActifTrue();

    List<Service> findByCategorie(Categorie categorie);

    List<Service> findByActifTrueOrderByOrdreAffichageAsc();

    long countByActifTrue();
}
