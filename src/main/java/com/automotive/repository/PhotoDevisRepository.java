package com.automotive.repository;

import com.automotive.model.PhotoDevis;
import com.automotive.model.Devis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoDevisRepository extends JpaRepository<PhotoDevis, Long> {
    List<PhotoDevis> findByDevis(Devis devis);
}
