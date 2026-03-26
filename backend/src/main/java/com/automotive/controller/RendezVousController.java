package com.automotive.controller;

import com.automotive.dto.RendezVousDTO;
import com.automotive.dto.RdvNotificationDTO;
import com.automotive.model.User;
import com.automotive.model.Vehicule;
import com.automotive.model.Service;
import com.automotive.repository.VehiculeRepository;
import com.automotive.repository.ServiceRepository;
import com.automotive.service.RendezVousService;
import com.automotive.service.UserService;
import com.automotive.service.WebSocketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rendezvous")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private UserService userService;

    @Autowired
    private WebSocketNotificationService notificationService;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // ==================== ENDPOINTS CLIENT ====================

    /**
     * Obtenir mes rendez-vous (utilisateur connecté)
     * GET /api/rendezvous/mes-rendezvous
     */
    @GetMapping("/mes-rendezvous")
    public ResponseEntity<?> getMyRendezVous(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Non authentifié"));
        }
        String email = authentication.getName();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Session invalide, veuillez vous reconnecter"));
        }
        List<RendezVousDTO> rendezVous = rendezVousService.getRendezVousByUser(user.getId());
        return ResponseEntity.ok(rendezVous);
    }

    /**
     * Créer un rendez-vous (utilisateur connecté)
     * POST /api/rendezvous
     */
    @PostMapping
    public ResponseEntity<?> createRendezVous(Authentication authentication, @RequestBody RendezVousDTO rendezVousDTO) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Non authentifié"));
        }
        try {
            String email = authentication.getName();
            User user = userService.findByEmail(email).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Session invalide, veuillez vous reconnecter"));
            }
            rendezVousDTO.setUserId(user.getId());
            RendezVousDTO createdRendezVous = rendezVousService.createRendezVous(rendezVousDTO);
            
            // Fetch additional details for notification
            Vehicule vehicule = vehiculeRepository.findById(rendezVousDTO.getVehiculeId()).orElse(null);
            Service service = serviceRepository.findById(rendezVousDTO.getServiceId()).orElse(null);
            
            // Send WebSocket notification to admins
            RdvNotificationDTO notification = RdvNotificationDTO.builder()
                    .id(createdRendezVous.getId())
                    .userEmail(user.getEmail())
                    .userName(user.getPrenom() + " " + user.getNom())
                    .vehicule(vehicule != null ? vehicule.getMarque() + " " + vehicule.getModele() : "N/A")
                    .service(service != null ? service.getNom() : "N/A")
                    .dateRdv(createdRendezVous.getDateRdv() != null 
                        ? createdRendezVous.getDateRdv().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) 
                        : "N/A")
                    .statut(createdRendezVous.getStatut())
                    .message("Nouveau rendez-vous créé par " + user.getPrenom() + " " + user.getNom())
                    .build();
            
            notificationService.notifyNewRendezVous(notification);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRendezVous);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * Annuler un rendez-vous (utilisateur connecté)
     * PUT /api/rendezvous/{id}/annuler
     */
    @PutMapping("/{id}/annuler")
    public ResponseEntity<?> annulerRendezVous(Authentication authentication, @PathVariable Long id) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Non authentifié"));
        }
        String email = authentication.getName();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Session invalide, veuillez vous reconnecter"));
        }

        // Vérifier que le RDV appartient à l'utilisateur
        RendezVousDTO rdv = rendezVousService.getRendezVousById(id);
        if (rdv == null || !rdv.getUserId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Non autorisé"));
        }

        RendezVousDTO updatedRendezVous = rendezVousService.updateStatut(id, "ANNULE");
        return updatedRendezVous != null ? ResponseEntity.ok(updatedRendezVous) : ResponseEntity.notFound().build();
    }

    /**
     * Obtenir un rendez-vous par ID
     * GET /api/rendezvous/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<RendezVousDTO> getRendezVousById(@PathVariable Long id) {
        RendezVousDTO rendezVous = rendezVousService.getRendezVousById(id);
        return rendezVous != null ? ResponseEntity.ok(rendezVous) : ResponseEntity.notFound().build();
    }

    // ==================== ENDPOINTS ADMIN ====================

    /**
     * Obtenir tous les rendez-vous (Admin)
     * GET /api/rendezvous/all
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RendezVousDTO>> getAllRendezVous() {
        List<RendezVousDTO> rendezVous = rendezVousService.getAllRendezVous();
        return ResponseEntity.ok(rendezVous);
    }

    /**
     * Obtenir les rendez-vous d'un utilisateur (Admin)
     * GET /api/rendezvous/user/{userId}
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RendezVousDTO>> getRendezVousByUser(@PathVariable Long userId) {
        List<RendezVousDTO> rendezVous = rendezVousService.getRendezVousByUser(userId);
        return ResponseEntity.ok(rendezVous);
    }

    /**
     * Obtenir les rendez-vous par statut (Admin)
     * GET /api/rendezvous/statut/{statut}
     */
    @GetMapping("/statut/{statut}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RendezVousDTO>> getRendezVousByStatut(@PathVariable String statut) {
        List<RendezVousDTO> rendezVous = rendezVousService.getRendezVousByStatut(statut);
        return ResponseEntity.ok(rendezVous);
    }

    /**
     * Modifier un rendez-vous (Admin)
     * PUT /api/rendezvous/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RendezVousDTO> updateRendezVous(@PathVariable Long id,
            @RequestBody RendezVousDTO rendezVousDTO) {
        RendezVousDTO updatedRendezVous = rendezVousService.updateRendezVous(id, rendezVousDTO);
        return updatedRendezVous != null ? ResponseEntity.ok(updatedRendezVous) : ResponseEntity.notFound().build();
    }

    /**
     * Modifier le statut d'un rendez-vous (Admin)
     * PUT /api/rendezvous/{id}/statut/{statut}
     */
    @PutMapping("/{id}/statut/{statut}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RendezVousDTO> updateStatut(@PathVariable Long id, @PathVariable String statut) {
        RendezVousDTO updatedRendezVous = rendezVousService.updateStatut(id, statut);
        return updatedRendezVous != null ? ResponseEntity.ok(updatedRendezVous) : ResponseEntity.notFound().build();
    }

    /**
     * Supprimer un rendez-vous (Admin)
     * DELETE /api/rendezvous/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        return rendezVousService.deleteRendezVous(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
