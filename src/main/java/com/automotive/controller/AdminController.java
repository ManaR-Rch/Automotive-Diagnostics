package com.automotive.controller;

import com.automotive.dto.*;
import com.automotive.service.RendezVousService;
import com.automotive.service.ServiceService;
import com.automotive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  @Autowired
  private UserService userService;

  @Autowired
  private ServiceService serviceService;

  @Autowired
  private RendezVousService rendezVousService;

  // ==================== STATISTIQUES ====================

  /**
   * Obtenir les statistiques du dashboard
   * GET /api/admin/stats
   */
  @GetMapping("/stats")
  public ResponseEntity<DashboardStats> getDashboardStats() {
    DashboardStats stats = DashboardStats.builder()
        .totalUsers(userService.countUsers())
        .totalClients(userService.countClients())
        .totalServices(serviceService.countServices())
        .activeServices(serviceService.countActiveServices())
        .totalRendezVous(rendezVousService.countRendezVous())
        .rdvConfirmes(rendezVousService.countByStatut("CONFIRME"))
        .rdvEnCours(rendezVousService.countByStatut("EN_COURS"))
        .rdvTermines(rendezVousService.countByStatut("TERMINE"))
        .rdvAnnules(rendezVousService.countByStatut("ANNULE"))
        .build();
    return ResponseEntity.ok(stats);
  }

  // ==================== GESTION UTILISATEURS ====================

  /**
   * Obtenir tous les utilisateurs
   * GET /api/admin/users
   */
  @GetMapping("/users")
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<UserDTO> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  /**
   * Obtenir tous les clients
   * GET /api/admin/users/clients
   */
  @GetMapping("/users/clients")
  public ResponseEntity<List<UserDTO>> getAllClients() {
    List<UserDTO> clients = userService.getAllClients();
    return ResponseEntity.ok(clients);
  }

  /**
   * Obtenir un utilisateur par ID
   * GET /api/admin/users/{id}
   */
  @GetMapping("/users/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    UserDTO user = userService.getUserById(id);
    return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
  }

  /**
   * Créer un utilisateur (avec rôle)
   * POST /api/admin/users
   */
  @PostMapping("/users")
  public ResponseEntity<?> createUser(@RequestBody Map<String, Object> request) {
    try {
      UserDTO userDTO = UserDTO.builder()
          .nom((String) request.get("nom"))
          .prenom((String) request.get("prenom"))
          .email((String) request.get("email"))
          .telephone((String) request.get("telephone"))
          .role((String) request.get("role"))
          .build();
      String password = (String) request.get("motDePasse");
      UserDTO createdUser = userService.createUser(userDTO, password);
      return ResponseEntity.ok(createdUser);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
  }

  /**
   * Modifier un utilisateur
   * PUT /api/admin/users/{id}
   */
  @PutMapping("/users/{id}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
    UserDTO updatedUser = userService.updateUser(id, userDTO);
    return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
  }

  /**
   * Activer/Désactiver un utilisateur
   * PUT /api/admin/users/{id}/toggle
   */
  @PutMapping("/users/{id}/toggle")
  public ResponseEntity<UserDTO> toggleUserStatus(@PathVariable Long id) {
    UserDTO user = userService.toggleUserStatus(id);
    return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
  }

  /**
   * Supprimer un utilisateur
   * DELETE /api/admin/users/{id}
   */
  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    return userService.deleteUser(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  // ==================== GESTION RENDEZ-VOUS ====================

  /**
   * Obtenir tous les rendez-vous
   * GET /api/admin/rendezvous
   */
  @GetMapping("/rendezvous")
  public ResponseEntity<List<RendezVousDTO>> getAllRendezVous() {
    List<RendezVousDTO> rendezVous = rendezVousService.getAllRendezVous();
    return ResponseEntity.ok(rendezVous);
  }

  /**
   * Obtenir les rendez-vous par statut
   * GET /api/admin/rendezvous/statut/{statut}
   */
  @GetMapping("/rendezvous/statut/{statut}")
  public ResponseEntity<List<RendezVousDTO>> getRendezVousByStatut(@PathVariable String statut) {
    List<RendezVousDTO> rendezVous = rendezVousService.getRendezVousByStatut(statut);
    return ResponseEntity.ok(rendezVous);
  }

  /**
   * Modifier le statut d'un rendez-vous
   * PUT /api/admin/rendezvous/{id}/statut/{statut}
   */
  @PutMapping("/rendezvous/{id}/statut/{statut}")
  public ResponseEntity<RendezVousDTO> updateRendezVousStatut(@PathVariable Long id, @PathVariable String statut) {
    RendezVousDTO updatedRendezVous = rendezVousService.updateStatut(id, statut);
    return updatedRendezVous != null ? ResponseEntity.ok(updatedRendezVous) : ResponseEntity.notFound().build();
  }

  // ==================== GESTION SERVICES ====================

  /**
   * Obtenir tous les services
   * GET /api/admin/services
   */
  @GetMapping("/services")
  public ResponseEntity<List<ServiceDTO>> getAllServices() {
    List<ServiceDTO> services = serviceService.getAllServices();
    return ResponseEntity.ok(services);
  }

  /**
   * Créer un service
   * POST /api/admin/services
   */
  @PostMapping("/services")
  public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO) {
    ServiceDTO createdService = serviceService.createService(serviceDTO);
    return ResponseEntity.ok(createdService);
  }

  /**
   * Modifier un service
   * PUT /api/admin/services/{id}
   */
  @PutMapping("/services/{id}")
  public ResponseEntity<ServiceDTO> updateService(@PathVariable Long id, @RequestBody ServiceDTO serviceDTO) {
    ServiceDTO updatedService = serviceService.updateService(id, serviceDTO);
    return updatedService != null ? ResponseEntity.ok(updatedService) : ResponseEntity.notFound().build();
  }

  /**
   * Activer/Désactiver un service
   * PUT /api/admin/services/{id}/toggle
   */
  @PutMapping("/services/{id}/toggle")
  public ResponseEntity<ServiceDTO> toggleServiceStatus(@PathVariable Long id) {
    ServiceDTO service = serviceService.toggleServiceStatus(id);
    return service != null ? ResponseEntity.ok(service) : ResponseEntity.notFound().build();
  }

  /**
   * Supprimer un service
   * DELETE /api/admin/services/{id}
   */
  @DeleteMapping("/services/{id}")
  public ResponseEntity<Void> deleteService(@PathVariable Long id) {
    return serviceService.deleteService(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}
