package com.automotive.controller;

import com.automotive.dto.DevisDTO;
import com.automotive.service.DevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/devis")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DevisController {

    @Autowired
    private DevisService devisService;

    @GetMapping("/{id}")
    public ResponseEntity<DevisDTO> getDevisById(@PathVariable Long id) {
        DevisDTO devis = devisService.getDevisById(id);
        return devis != null ? ResponseEntity.ok(devis) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DevisDTO>> getDevisByUser(@PathVariable Long userId) {
        List<DevisDTO> devis = devisService.getDevisByUser(userId);
        return ResponseEntity.ok(devis);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<DevisDTO>> getDevisByStatut(@PathVariable String statut) {
        List<DevisDTO> devis = devisService.getDevisByStatut(statut);
        return ResponseEntity.ok(devis);
    }

    @PostMapping
    public ResponseEntity<DevisDTO> createDevis(@RequestBody DevisDTO devisDTO) {
        try {
            DevisDTO createdDevis = devisService.createDevis(devisDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDevis);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/statut/{statut}")
    public ResponseEntity<DevisDTO> updateDevisStatut(@PathVariable Long id, @PathVariable String statut) {
        DevisDTO updatedDevis = devisService.updateDevisStatut(id, statut);
        return updatedDevis != null ? ResponseEntity.ok(updatedDevis) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/montants")
    public ResponseEntity<DevisDTO> updateDevisMontants(@PathVariable Long id, @RequestBody DevisDTO devisDTO) {
        DevisDTO updatedDevis = devisService.updateDevisMontants(id, devisDTO);
        return updatedDevis != null ? ResponseEntity.ok(updatedDevis) : ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/en-attente/count")
    public ResponseEntity<Long> countDevisEnAttente() {
        long count = devisService.countDevisEnAttente();
        return ResponseEntity.ok(count);
    }
}
