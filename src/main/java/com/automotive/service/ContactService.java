package com.automotive.service;

import com.automotive.dto.ContactDTO;
import com.automotive.model.Contact;
import com.automotive.model.User;
import com.automotive.repository.ContactRepository;
import com.automotive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    public ContactDTO getContactById(Long id) {
        return contactRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ContactDTO> getContactsByStatut(String statut) {
        return contactRepository.findByStatut(Contact.Statut.valueOf(statut))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO createContact(ContactDTO contactDTO) {
        User user = null;
        if (contactDTO.getUserId() != null) {
            user = userRepository.findById(contactDTO.getUserId()).orElse(null);
        }

        Contact contact = convertToEntity(contactDTO);
        contact.setUser(user);
        contact.setStatut(Contact.Statut.NOUVEAU);

        Contact savedContact = contactRepository.save(contact);
        return convertToDTO(savedContact);
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setNom(contactDTO.getNom());
                    contact.setPrenom(contactDTO.getPrenom());
                    contact.setEmail(contactDTO.getEmail());
                    contact.setTelephone(contactDTO.getTelephone());
                    contact.setSujet(Contact.Sujet.valueOf(contactDTO.getSujet()));
                    contact.setMessage(contactDTO.getMessage());
                    return convertToDTO(contactRepository.save(contact));
                })
                .orElse(null);
    }

    public ContactDTO markAsProcessed(Long id, String notes) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setStatut(Contact.Statut.TRAITE);
                    contact.setDateTraitement(LocalDateTime.now());
                    return convertToDTO(contactRepository.save(contact));
                })
                .orElse(null);
    }

    public boolean deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ContactDTO convertToDTO(Contact contact) {
        return ContactDTO.builder()
                .id(contact.getId())
                .userId(contact.getUser() != null ? contact.getUser().getId() : null)
                .nom(contact.getNom())
                .prenom(contact.getPrenom())
                .email(contact.getEmail())
                .telephone(contact.getTelephone())
                .sujet(contact.getSujet().toString())
                .message(contact.getMessage())
                .statut(contact.getStatut().toString())
                .dateEnvoi(contact.getDateEnvoi())
                .dateTraitement(contact.getDateTraitement())
                .build();
    }

    private Contact convertToEntity(ContactDTO contactDTO) {
        return Contact.builder()
                .nom(contactDTO.getNom())
                .prenom(contactDTO.getPrenom())
                .email(contactDTO.getEmail())
                .telephone(contactDTO.getTelephone())
                .sujet(Contact.Sujet.valueOf(contactDTO.getSujet()))
                .message(contactDTO.getMessage())
                .build();
    }
}
