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
public class DevisDTO {
    private Long id;
    private Long userId;
    private Long vehiculeId;
    private Long serviceId;
    private String descriptionDemande;
    private String delaiSouhaite;
    private Boolean piecesOrigine;
    private String statut;
    private Double montantMainOeuvre;
    private Double montantPieces;
    private Double montantFournitures;
    private Double montantHt;
    private Double montantTva;
    private Double montantTtc;
    private String commentaireAdmin;
    private String commentaireClient;
    private Boolean estAutomatique;
    private LocalDateTime dateDemande;
    private LocalDateTime dateEvaluation;
    private LocalDateTime dateExpiration;
    private String pdfPath;
}
