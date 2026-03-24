package com.automotive.controller;

import com.automotive.dto.*;
import com.automotive.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

  @Autowired
  private UserService userService;

  /**
   * Inscription d'un nouvel utilisateur
   * POST /api/auth/register
   */
  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
    try {
      AuthResponse response = userService.register(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
  }

  /**
   * Connexion d'un utilisateur
   * POST /api/auth/login
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
    try {
      AuthResponse response = userService.login(request);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
    }
  }

  /**
   * Obtenir le profil de l'utilisateur connecté
   * GET /api/auth/profile
   */
  @GetMapping("/profile")
  public ResponseEntity<?> getProfile(Authentication authentication) {
    if (authentication == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Non authentifié"));
    }
    String email = authentication.getName();
    UserDTO user = userService.getProfile(email);
    if (user != null) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Modifier le profil de l'utilisateur connecté
   * PUT /api/auth/profile
   */
  @PutMapping("/profile")
  public ResponseEntity<?> updateProfile(Authentication authentication,
      @Valid @RequestBody UpdateProfileRequest request) {
    if (authentication == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Non authentifié"));
    }
    String email = authentication.getName();
    UserDTO user = userService.updateProfile(email, request);
    if (user != null) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Changer le mot de passe
   * PUT /api/auth/password
   */
  @PutMapping("/password")
  public ResponseEntity<?> changePassword(Authentication authentication,
      @Valid @RequestBody ChangePasswordRequest request) {
    if (authentication == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Non authentifié"));
    }
    try {
      String email = authentication.getName();
      boolean success = userService.changePassword(email, request);
      if (success) {
        return ResponseEntity.ok(Map.of("message", "Mot de passe modifié avec succès"));
      }
      return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
  }
}
