package com.automotive.repository;

import com.automotive.model.Vehicule;
import com.automotive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByUser(User user);

    Optional<Vehicule> findByVin(String vin);

    List<Vehicule> findByUserAndEstPrincipalTrue(User user);
}
