package com.automotive.controller;

import com.automotive.dto.VehiculeDTO;
import com.automotive.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VehiculeController {

    @Autowired
    private VehiculeService vehiculeService;

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> getVehiculeById(@PathVariable Long id) {
        VehiculeDTO vehicule = vehiculeService.getVehiculeById(id);
        return vehicule != null ? ResponseEntity.ok(vehicule) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VehiculeDTO>> getVehiculesByUser(@PathVariable Long userId) {
        List<VehiculeDTO> vehicules = vehiculeService.getVehiculesByUser(userId);
        return ResponseEntity.ok(vehicules);
    }

    @PostMapping
    public ResponseEntity<VehiculeDTO> createVehicule(@RequestBody VehiculeDTO vehiculeDTO) {
        VehiculeDTO createdVehicule = vehiculeService.createVehicule(vehiculeDTO);
        return createdVehicule != null ? ResponseEntity.status(HttpStatus.CREATED).body(createdVehicule) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeDTO> updateVehicule(@PathVariable Long id, @RequestBody VehiculeDTO vehiculeDTO) {
        VehiculeDTO updatedVehicule = vehiculeService.updateVehicule(id, vehiculeDTO);
        return updatedVehicule != null ? ResponseEntity.ok(updatedVehicule) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        return vehiculeService.deleteVehicule(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
