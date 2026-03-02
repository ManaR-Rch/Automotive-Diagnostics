package com.automotive.controller;

import com.automotive.dto.MarqueDTO;
import com.automotive.service.MarqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/marques")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MarqueController {

    @Autowired
    private MarqueService marqueService;

    @GetMapping("/{id}")
    public ResponseEntity<MarqueDTO> getMarqueById(@PathVariable Long id) {
        MarqueDTO marque = marqueService.getMarqueById(id);
        return marque != null ? ResponseEntity.ok(marque) : ResponseEntity.notFound().build();
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<MarqueDTO> getMarqueByNom(@PathVariable String nom) {
        MarqueDTO marque = marqueService.getMarqueByNom(nom);
        return marque != null ? ResponseEntity.ok(marque) : ResponseEntity.notFound().build();
    }

    @GetMapping("/actives")
    public ResponseEntity<List<MarqueDTO>> getAllActiveMarques() {
        List<MarqueDTO> marques = marqueService.getAllActiveMarques();
        return ResponseEntity.ok(marques);
    }

    @GetMapping("/partenaires")
    public ResponseEntity<List<MarqueDTO>> getPartnerMarques() {
        List<MarqueDTO> marques = marqueService.getPartnerMarques();
        return ResponseEntity.ok(marques);
    }

    @PostMapping
    public ResponseEntity<MarqueDTO> createMarque(@RequestBody MarqueDTO marqueDTO) {
        MarqueDTO createdMarque = marqueService.createMarque(marqueDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMarque);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarqueDTO> updateMarque(@PathVariable Long id, @RequestBody MarqueDTO marqueDTO) {
        MarqueDTO updatedMarque = marqueService.updateMarque(id, marqueDTO);
        return updatedMarque != null ? ResponseEntity.ok(updatedMarque) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarque(@PathVariable Long id) {
        return marqueService.deleteMarque(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
