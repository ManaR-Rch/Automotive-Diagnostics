package com.automotive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdvNotificationDTO {
  private Long id;
  private String userEmail;
  private String userName;
  private String vehicule;
  private String service;
  private String dateRdv;
  private String statut;
  private String message;
  private String timestamp;
}
