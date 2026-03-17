package com.automotive.repository;

import com.automotive.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByActifTrue();

    List<Service> findByCategorie(Service.Categorie categorie);

    List<Service> findByActifTrueOrderByOrdreAffichageAsc();

    long countByActifTrue();
}
