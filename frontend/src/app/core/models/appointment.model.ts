export interface Appointment {
  id?: number;
  userId?: number;
  vehiculeId: number;
  serviceId: number;
  dateRdv: string;
  heureDebut?: string;
  heureFin?: string;
  descriptionProbleme?: string;
  urgence?: 'FAIBLE' | 'NORMALE' | 'HAUTE' | 'CRITIQUE';
  statut?: 'EN_ATTENTE' | 'CONFIRME' | 'EN_COURS' | 'TERMINE' | 'ANNULE';
  notesAdmin?: string;
  dateCreation?: string;
  dateModification?: string;
}

export interface DashboardStats {
  totalUsers: number;
  totalClients: number;
  totalServices: number;
  activeServices: number;
  totalRendezVous: number;
  rdvConfirmes: number;
  rdvEnCours: number;
  rdvTermines: number;
  rdvAnnules: number;
}
