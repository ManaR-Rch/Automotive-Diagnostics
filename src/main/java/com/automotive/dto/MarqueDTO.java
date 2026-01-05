package com.automotive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarqueDTO {
    private Long id;
    private String nom;
    private String logoPath;
    private Boolean estPartenaire;
    private Integer ordreAffichage;
    private Boolean actif;
}
