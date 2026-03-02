package com.automotive.controller;

import com.automotive.dto.ContactDTO;
import com.automotive.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        ContactDTO contact = contactService.getContactById(id);
        return contact != null ? ResponseEntity.ok(contact) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        List<ContactDTO> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ContactDTO>> getContactsByStatut(@PathVariable String statut) {
        List<ContactDTO> contacts = contactService.getContactsByStatut(statut);
        return ResponseEntity.ok(contacts);
    }

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        try {
            ContactDTO createdContact = contactService.createContact(contactDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        ContactDTO updatedContact = contactService.updateContact(id, contactDTO);
        return updatedContact != null ? ResponseEntity.ok(updatedContact) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/traiter")
    public ResponseEntity<ContactDTO> markAsProcessed(@PathVariable Long id, @RequestParam(required = false) String notes) {
        ContactDTO processedContact = contactService.markAsProcessed(id, notes);
        return processedContact != null ? ResponseEntity.ok(processedContact) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        return contactService.deleteContact(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
