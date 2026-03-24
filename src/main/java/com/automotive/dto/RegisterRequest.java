package com.automotive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

  @NotBlank(message = "Le nom est obligatoire")
  private String nom;

  @NotBlank(message = "Le prénom est obligatoire")
  private String prenom;

  @NotBlank(message = "L'email est obligatoire")
  @Email(message = "Email invalide")
  private String email;

  @NotBlank(message = "Le téléphone est obligatoire")
  private String telephone;

  @NotBlank(message = "Le mot de passe est obligatoire")
  @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
  private String motDePasse;
}
