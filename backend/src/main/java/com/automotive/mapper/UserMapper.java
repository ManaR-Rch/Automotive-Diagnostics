package com.automotive.mapper;

import com.automotive.dto.UserDTO;
import com.automotive.model.User;
import com.automotive.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDTO toDTO(User entity) {
    if (entity == null) {
      return null;
    }

    return UserDTO.builder()
        .id(entity.getId())
        .nom(entity.getNom())
        .prenom(entity.getPrenom())
        .email(entity.getEmail())
        .telephone(entity.getTelephone())
        .role(entity.getRole() != null ? entity.getRole().name() : null)
        .actif(entity.getActif())
        .build();
  }

  public User toEntity(UserDTO dto) {
    if (dto == null) {
      return null;
    }

    return User.builder()
        .id(dto.getId())
        .nom(dto.getNom())
        .prenom(dto.getPrenom())
        .email(dto.getEmail())
        .telephone(dto.getTelephone())
        .role(dto.getRole() != null ? Role.valueOf(dto.getRole()) : null)
        .actif(dto.getActif())
        .build();
  }
}