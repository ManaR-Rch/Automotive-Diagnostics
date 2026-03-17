package com.automotive.controller;

import com.automotive.dto.VehiculeDTO;
import com.automotive.model.User;
import com.automotive.service.UserService;
import com.automotive.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VehiculeController {

    @Autowired
    private VehiculeService vehiculeService;

    @Autowired
    private UserService userService;

    /**
     * Obtenir les véhicules de l'utilisateur connecté
     * GET /api/vehicules/mes-vehicules
     */
    @GetMapping("/mes-vehicules")
    public ResponseEntity<?> getMyVehicules(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Non authentifié"));
        }
        String email = authentication.getName();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Session invalide, veuillez vous reconnecter"));
        }
        List<VehiculeDTO> vehicules = vehiculeService.getVehiculesByUser(user.getId());
        return ResponseEntity.ok(vehicules);
    }

    /**
     * Obtenir un véhicule par ID
     * GET /api/vehicules/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> getVehiculeById(@PathVariable Long id) {
        VehiculeDTO vehicule = vehiculeService.getVehiculeById(id);
        return vehicule != null ? ResponseEntity.ok(vehicule) : ResponseEntity.notFound().build();
    }

    /**
     * Obtenir les véhicules d'un utilisateur (Admin ou propriétaire)
     * GET /api/vehicules/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VehiculeDTO>> getVehiculesByUser(@PathVariable Long userId) {
        List<VehiculeDTO> vehicules = vehiculeService.getVehiculesByUser(userId);
        return ResponseEntity.ok(vehicules);
    }

    /**
     * Ajouter un véhicule pour l'utilisateur connecté
     * POST /api/vehicules
     */
    @PostMapping
    public ResponseEntity<?> createVehicule(Authentication authentication, @RequestBody VehiculeDTO vehiculeDTO) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Non authentifié"));
        }
        String email = authentication.getName();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Session invalide, veuillez vous reconnecter"));
        }
        vehiculeDTO.setUserId(user.getId());
        VehiculeDTO createdVehicule = vehiculeService.createVehicule(vehiculeDTO);
        return createdVehicule != null ? ResponseEntity.status(HttpStatus.CREATED).body(createdVehicule)
                : ResponseEntity.badRequest().build();
    }

    /**
     * Modifier un véhicule
     * PUT /api/vehicules/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<VehiculeDTO> updateVehicule(@PathVariable Long id, @RequestBody VehiculeDTO vehiculeDTO) {
        VehiculeDTO updatedVehicule = vehiculeService.updateVehicule(id, vehiculeDTO);
        return updatedVehicule != null ? ResponseEntity.ok(updatedVehicule) : ResponseEntity.notFound().build();
    }

    /**
     * Supprimer un véhicule
     * DELETE /api/vehicules/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        return vehiculeService.deleteVehicule(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
