package com.automotive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStats {
  private long totalUsers;
  private long totalClients;
  private long totalServices;
  private long activeServices;
  private long totalRendezVous;
  private long rdvConfirmes;
  private long rdvEnCours;
  private long rdvTermines;
  private long rdvAnnules;
}
