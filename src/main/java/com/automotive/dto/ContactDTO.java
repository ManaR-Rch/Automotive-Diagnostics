package com.automotive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String sujet;
    private String message;
    private String statut;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateTraitement;
}
