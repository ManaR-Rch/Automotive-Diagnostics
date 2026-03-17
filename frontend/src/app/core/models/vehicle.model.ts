export interface Vehicle {
  id?: number;
  userId?: number;
  marque: string;
  modele: string;
  annee: number;
  carburant: string;
  kilometrage: number;
  vin?: string;
  estPrincipal?: boolean;
}
