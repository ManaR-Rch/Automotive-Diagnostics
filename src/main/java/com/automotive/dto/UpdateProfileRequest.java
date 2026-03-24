package com.automotive.dto;

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
public class UpdateProfileRequest {

  @NotBlank(message = "Le nom est obligatoire")
  private String nom;

  @NotBlank(message = "Le prénom est obligatoire")
  private String prenom;

  @NotBlank(message = "Le téléphone est obligatoire")
  private String telephone;
}
