package com.automotive.controller;

import com.automotive.dto.RendezVousDTO;
import com.automotive.service.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rendezvous")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @GetMapping("/{id}")
    public ResponseEntity<RendezVousDTO> getRendezVousById(@PathVariable Long id) {
        RendezVousDTO rendezVous = rendezVousService.getRendezVousById(id);
        return rendezVous != null ? ResponseEntity.ok(rendezVous) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RendezVousDTO>> getRendezVousByUser(@PathVariable Long userId) {
        List<RendezVousDTO> rendezVous = rendezVousService.getRendezVousByUser(userId);
        return ResponseEntity.ok(rendezVous);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<RendezVousDTO>> getRendezVousByStatut(@PathVariable String statut) {
        List<RendezVousDTO> rendezVous = rendezVousService.getRendezVousByStatut(statut);
        return ResponseEntity.ok(rendezVous);
    }

    @PostMapping
    public ResponseEntity<RendezVousDTO> createRendezVous(@RequestBody RendezVousDTO rendezVousDTO) {
        try {
            RendezVousDTO createdRendezVous = rendezVousService.createRendezVous(rendezVousDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRendezVous);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVousDTO> updateRendezVous(@PathVariable Long id, @RequestBody RendezVousDTO rendezVousDTO) {
        RendezVousDTO updatedRendezVous = rendezVousService.updateRendezVous(id, rendezVousDTO);
        return updatedRendezVous != null ? ResponseEntity.ok(updatedRendezVous) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/statut/{statut}")
    public ResponseEntity<RendezVousDTO> updateStatut(@PathVariable Long id, @PathVariable String statut) {
        RendezVousDTO updatedRendezVous = rendezVousService.updateStatut(id, statut);
        return updatedRendezVous != null ? ResponseEntity.ok(updatedRendezVous) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        return rendezVousService.deleteRendezVous(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
