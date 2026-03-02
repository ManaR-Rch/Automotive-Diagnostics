package com.automotive.controller;

import com.automotive.dto.HoraireDTO;
import com.automotive.service.HoraireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/horaires")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HoraireController {

    @Autowired
    private HoraireService horaireService;

    @GetMapping("/{id}")
    public ResponseEntity<HoraireDTO> getHoraireById(@PathVariable Long id) {
        HoraireDTO horaire = horaireService.getHoraireById(id);
        return horaire != null ? ResponseEntity.ok(horaire) : ResponseEntity.notFound().build();
    }

    @GetMapping("/jour/{jour}")
    public ResponseEntity<HoraireDTO> getHoraireByJour(@PathVariable String jour) {
        HoraireDTO horaire = horaireService.getHoraireByJour(jour);
        return horaire != null ? ResponseEntity.ok(horaire) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<HoraireDTO>> getAllHoraires() {
        List<HoraireDTO> horaires = horaireService.getAllHoraires();
        return ResponseEntity.ok(horaires);
    }

    @PostMapping
    public ResponseEntity<HoraireDTO> createHoraire(@RequestBody HoraireDTO horaireDTO) {
        HoraireDTO createdHoraire = horaireService.createHoraire(horaireDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHoraire);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HoraireDTO> updateHoraire(@PathVariable Long id, @RequestBody HoraireDTO horaireDTO) {
        HoraireDTO updatedHoraire = horaireService.updateHoraire(id, horaireDTO);
        return updatedHoraire != null ? ResponseEntity.ok(updatedHoraire) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoraire(@PathVariable Long id) {
        return horaireService.deleteHoraire(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
