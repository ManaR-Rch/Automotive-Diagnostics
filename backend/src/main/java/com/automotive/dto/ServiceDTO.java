package com.automotive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDTO {
    private Long id;
    private String nom;
    private String description;
    private String categorie;
    private Integer dureeEstimee;
    private Double prixMin;
    private Double prixMax;
    private Boolean actif;
    private Integer ordreAffichage;
}
