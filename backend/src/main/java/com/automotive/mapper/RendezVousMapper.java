package com.automotive.mapper;

import com.automotive.dto.RendezVousDTO;
import com.automotive.model.RendezVous;
import com.automotive.enums.Statut;
import com.automotive.enums.Urgence;
import org.springframework.stereotype.Component;

@Component
public class RendezVousMapper {

  public RendezVousDTO toDTO(RendezVous entity) {
    if (entity == null) {
      return null;
    }

    return RendezVousDTO.builder()
        .id(entity.getId())
        .userId(entity.getUser() != null ? entity.getUser().getId() : null)
        .vehiculeId(entity.getVehicule() != null ? entity.getVehicule().getId() : null)
        .serviceId(entity.getService() != null ? entity.getService().getId() : null)
        .dateRdv(entity.getDateRdv())
        .heureDebut(entity.getHeureDebut())
        .heureFin(entity.getHeureFin())
        .descriptionProbleme(entity.getDescriptionProbleme())
        .urgence(entity.getUrgence() != null ? entity.getUrgence().name() : null)
        .statut(entity.getStatut() != null ? entity.getStatut().name() : null)
        .notesAdmin(entity.getNotesAdmin())
        .dateCreation(entity.getDateCreation())
        .dateModification(entity.getDateModification())
        .build();
  }

  public RendezVous toEntity(RendezVousDTO dto) {
    if (dto == null) {
      return null;
    }

    return RendezVous.builder()
        .id(dto.getId())
        .dateRdv(dto.getDateRdv())
        .heureDebut(dto.getHeureDebut())
        .heureFin(dto.getHeureFin())
        .descriptionProbleme(dto.getDescriptionProbleme())
        .urgence(dto.getUrgence() != null ? Urgence.valueOf(dto.getUrgence()) : null)
        .statut(dto.getStatut() != null ? Statut.valueOf(dto.getStatut()) : null)
        .notesAdmin(dto.getNotesAdmin())
        .build();
  }
}