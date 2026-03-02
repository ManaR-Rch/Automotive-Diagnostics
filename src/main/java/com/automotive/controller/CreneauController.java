package com.automotive.controller;

import com.automotive.dto.CreneauDTO;
import com.automotive.service.CreneauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/creneaux")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CreneauController {

    @Autowired
    private CreneauService creneauService;

    @GetMapping("/{id}")
    public ResponseEntity<CreneauDTO> getCreneauById(@PathVariable Long id) {
        CreneauDTO creneau = creneauService.getCreneauById(id);
        return creneau != null ? ResponseEntity.ok(creneau) : ResponseEntity.notFound().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<CreneauDTO>> getCreneauxDisponibles() {
        List<CreneauDTO> creneaux = creneauService.getCreneauxDisponibles();
        return ResponseEntity.ok(creneaux);
    }

    @GetMapping("/range")
    public ResponseEntity<List<CreneauDTO>> getCreneauxByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        List<CreneauDTO> creneaux = creneauService.getCreneauxByDateRange(start, end);
        return ResponseEntity.ok(creneaux);
    }

    @PostMapping
    public ResponseEntity<CreneauDTO> createCreneau(@RequestBody CreneauDTO creneauDTO) {
        CreneauDTO createdCreneau = creneauService.createCreneau(creneauDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCreneau);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreneauDTO> updateCreneau(@PathVariable Long id, @RequestBody CreneauDTO creneauDTO) {
        CreneauDTO updatedCreneau = creneauService.updateCreneau(id, creneauDTO);
        return updatedCreneau != null ? ResponseEntity.ok(updatedCreneau) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreneau(@PathVariable Long id) {
        return creneauService.deleteCreneau(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
