package com.automotive.mapper;

import com.automotive.dto.ServiceDTO;
import com.automotive.model.Service;
import com.automotive.enums.Categorie;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

  public ServiceDTO toDTO(Service entity) {
    if (entity == null) {
      return null;
    }

    return ServiceDTO.builder()
        .id(entity.getId())
        .nom(entity.getNom())
        .description(entity.getDescription())
        .categorie(entity.getCategorie() != null ? entity.getCategorie().name() : null)
        .dureeEstimee(entity.getDureeEstimee())
        .prixMin(entity.getPrixMin())
        .prixMax(entity.getPrixMax())
        .actif(entity.getActif())
        .ordreAffichage(entity.getOrdreAffichage())
        .build();
  }

  public Service toEntity(ServiceDTO dto) {
    if (dto == null) {
      return null;
    }

    return Service.builder()
        .id(dto.getId())
        .nom(dto.getNom())
        .description(dto.getDescription())
        .categorie(dto.getCategorie() != null ? Categorie.valueOf(dto.getCategorie()) : null)
        .dureeEstimee(dto.getDureeEstimee())
        .prixMin(dto.getPrixMin())
        .prixMax(dto.getPrixMax())
        .actif(dto.getActif())
        .ordreAffichage(dto.getOrdreAffichage())
        .build();
  }
}