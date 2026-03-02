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
public class CreneauDTO {
    private Long id;
    private LocalDateTime dateCreneau;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Boolean disponible;
    private Long rdvId;
}
