package com.automotive.repository;

import com.automotive.model.User;
import com.automotive.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByTelephone(String telephone);

    boolean existsByEmail(String email);

    boolean existsByTelephone(String telephone);

    List<User> findByRole(Role role);

    List<User> findByActif(Boolean actif);

    long countByRole(Role role);
}
