package com.automotive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RendezVousDTO {
    private Long id;
    private Long userId;
    private Long vehiculeId;
    private Long serviceId;
    private LocalDateTime dateRdv;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String descriptionProbleme;
    private String urgence;
    private String statut;
    private String notesAdmin;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}
