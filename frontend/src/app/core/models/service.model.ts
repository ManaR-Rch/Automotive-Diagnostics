export interface Service {
  id?: number;
  nom: string;
  description: string;
  categorie: string;
  dureeEstimee: number;
  prixMin: number;
  prixMax: number;
  actif?: boolean;
  ordreAffichage?: number;
}
