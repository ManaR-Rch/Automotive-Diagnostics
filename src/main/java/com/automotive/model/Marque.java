package com.automotive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "marques")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nom;

    @Column(name = "logo_path")
    private String logoPath;

    @Column(name = "est_partenaire")
    private Boolean estPartenaire = false;

    @Column(name = "ordre_affichage")
    private Integer ordreAffichage;

    @Column(nullable = false)
    private Boolean actif = true;
}
