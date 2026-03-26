package com.automotive.controller;

import com.automotive.dto.ServiceDTO;
import com.automotive.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    /**
     * Voir tous les services actifs (accessible à tous)
     * GET /api/services
     */
    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAllActiveServices() {
        List<ServiceDTO> services = serviceService.getAllActiveServices();
        return ResponseEntity.ok(services);
    }

    /**
     * Voir tous les services (Admin)
     * GET /api/services/all
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<ServiceDTO> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    /**
     * Obtenir un service par ID
     * GET /api/services/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Long id) {
        ServiceDTO service = serviceService.getServiceById(id);
        return service != null ? ResponseEntity.ok(service) : ResponseEntity.notFound().build();
    }

    /**
     * Obtenir les services par catégorie
     * GET /api/services/categorie/{categorie}
     */
    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<ServiceDTO>> getServicesByCategorie(@PathVariable String categorie) {
        List<ServiceDTO> services = serviceService.getServicesByCategorie(categorie);
        return ResponseEntity.ok(services);
    }

    /**
     * Créer un service (Admin uniquement)
     * POST /api/services
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO) {
        ServiceDTO createdService = serviceService.createService(serviceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
    }

    /**
     * Modifier un service (Admin uniquement)
     * PUT /api/services/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable Long id, @RequestBody ServiceDTO serviceDTO) {
        ServiceDTO updatedService = serviceService.updateService(id, serviceDTO);
        return updatedService != null ? ResponseEntity.ok(updatedService) : ResponseEntity.notFound().build();
    }

    /**
     * Activer/Désactiver un service (Admin uniquement)
     * PUT /api/services/{id}/toggle
     */
    @PutMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDTO> toggleService(@PathVariable Long id) {
        ServiceDTO updatedService = serviceService.toggleServiceStatus(id);
        return updatedService != null ? ResponseEntity.ok(updatedService) : ResponseEntity.notFound().build();
    }

    /**
     * Supprimer un service (Admin uniquement)
     * DELETE /api/services/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        ServiceService.DeleteResult result = serviceService.deleteService(id);

        if (result == ServiceService.DeleteResult.NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }

        if (result == ServiceService.DeleteResult.DEACTIVATED) {
            return ResponseEntity.ok(Map.of(
                    "message", "Service lie a des rendez-vous: desactive au lieu d'etre supprime",
                    "mode", "DEACTIVATED"));
        }

        return ResponseEntity.noContent().build();
    }
}
