package com.automotive.repository;

import com.automotive.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByStatut(Contact.Statut statut);

    List<Contact> findByEmail(String email);
}
