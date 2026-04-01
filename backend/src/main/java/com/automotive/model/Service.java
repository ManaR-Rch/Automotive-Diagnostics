package com.automotive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import com.automotive.enums.Categorie;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categorie categorie;

    @Column(name = "duree_estimee")
    private Integer dureeEstimee;

    @Column(name = "prix_min")
    private Double prixMin;

    @Column(name = "prix_max")
    private Double prixMax;

    @Column(nullable = false)
    private Boolean actif = true;

    @Column(name = "ordre_affichage")
    private Integer ordreAffichage;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RendezVous> rendezVous;
}
