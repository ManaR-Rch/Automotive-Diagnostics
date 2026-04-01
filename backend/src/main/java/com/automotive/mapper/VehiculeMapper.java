package com.automotive.mapper;

import com.automotive.dto.VehiculeDTO;
import com.automotive.model.Vehicule;
import com.automotive.enums.Carburant;
import org.springframework.stereotype.Component;

@Component
public class VehiculeMapper {

  public VehiculeDTO toDTO(Vehicule entity) {
    if (entity == null) {
      return null;
    }

    return VehiculeDTO.builder()
        .id(entity.getId())
        .userId(entity.getUser() != null ? entity.getUser().getId() : null)
        .marque(entity.getMarque())
        .modele(entity.getModele())
        .annee(entity.getAnnee())
        .carburant(entity.getCarburant() != null ? entity.getCarburant().name() : null)
        .kilometrage(entity.getKilometrage())
        .vin(entity.getVin())
        .estPrincipal(entity.getEstPrincipal())
        .build();
  }

  public Vehicule toEntity(VehiculeDTO dto) {
    if (dto == null) {
      return null;
    }

    return Vehicule.builder()
        .id(dto.getId())
        .marque(dto.getMarque())
        .modele(dto.getModele())
        .annee(dto.getAnnee())
        .carburant(dto.getCarburant() != null ? Carburant.valueOf(dto.getCarburant()) : null)
        .kilometrage(dto.getKilometrage())
        .vin(dto.getVin())
        .estPrincipal(dto.getEstPrincipal())
        .build();
  }
}