package com.automotive.config;

import com.automotive.model.User;
import com.automotive.enums.Role;
import com.automotive.model.Service;
import com.automotive.enums.Categorie;
import com.automotive.repository.UserRepository;
import com.automotive.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ServiceRepository serviceRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    // Créer ou mettre à jour l'admin par défaut pour garantir l'accès en dev
    User admin = userRepository.findByEmail("admin@garage.com")
        .orElseGet(() -> User.builder()
            .nom("Admin")
            .prenom("Garage")
            .email("admin@garage.com")
            .telephone("0600000000")
            .role(Role.ADMIN)
            .actif(true)
            .build());

    admin.setNom("Admin");
    admin.setPrenom("Garage");
    admin.setEmail("admin@garage.com");
    admin.setTelephone("0600000000");
    admin.setRole(Role.ADMIN);
    admin.setActif(true);
    admin.setMotDePasse(passwordEncoder.encode("admin123"));
    userRepository.save(admin);
    System.out.println("Admin prêt: admin@garage.com / admin123");

    // Créer des services par défaut si la table est vide
    if (serviceRepository.count() == 0) {
      Service[] services = {
          Service.builder()
              .nom("Vidange")
              .description("Vidange huile moteur et filtre")
              .categorie(Categorie.MAINTENANCE)
              .dureeEstimee(60)
              .prixMin(50.0)
              .prixMax(80.0)
              .actif(true)
              .ordreAffichage(1)
              .build(),
          Service.builder()
              .nom("Révision complète")
              .description("Révision complète du véhicule incluant tous les contrôles")
              .categorie(Categorie.MAINTENANCE)
              .dureeEstimee(180)
              .prixMin(150.0)
              .prixMax(300.0)
              .actif(true)
              .ordreAffichage(2)
              .build(),
          Service.builder()
              .nom("Diagnostic électronique")
              .description("Diagnostic complet avec outil de diagnostic")
              .categorie(Categorie.DIAGNOSTIC)
              .dureeEstimee(45)
              .prixMin(40.0)
              .prixMax(60.0)
              .actif(true)
              .ordreAffichage(3)
              .build(),
          Service.builder()
              .nom("Changement de freins")
              .description("Remplacement des plaquettes et/ou disques de frein")
              .categorie(Categorie.REPARATION)
              .dureeEstimee(120)
              .prixMin(100.0)
              .prixMax(250.0)
              .actif(true)
              .ordreAffichage(4)
              .build(),
          Service.builder()
              .nom("Contrôle technique")
              .description("Passage au contrôle technique")
              .categorie(Categorie.CONTROLE_TECHNIQUE)
              .dureeEstimee(60)
              .prixMin(70.0)
              .prixMax(90.0)
              .actif(true)
              .ordreAffichage(5)
              .build(),
          Service.builder()
              .nom("Climatisation")
              .description("Recharge et vérification du système de climatisation")
              .categorie(Categorie.MAINTENANCE)
              .dureeEstimee(60)
              .prixMin(60.0)
              .prixMax(120.0)
              .actif(true)
              .ordreAffichage(6)
              .build()
      };

      for (Service service : services) {
        serviceRepository.save(service);
      }
      System.out.println("Services par défaut créés");
    }
  }
}
