package com.automotive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculeDTO {
    private Long id;
    private Long userId;
    private String marque;
    private String modele;
    private Integer annee;
    private String carburant;
    private Integer kilometrage;
    private String vin;
    private Boolean estPrincipal;
}
