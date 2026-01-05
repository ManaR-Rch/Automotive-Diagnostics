package com.automotive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "photo_devis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoDevis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "devis_id", nullable = false)
    private Devis devis;

    @Column(name = "nom_fichier", nullable = false)
    private String nomFichier;

    @Column(name = "chemin_fichier", nullable = false)
    private String cheminFichier;

    @Column(name = "taille")
    private Long taille;

    @Column(name = "date_upload")
    private LocalDateTime dateUpload;

    @PrePersist
    protected void onCreate() {
        dateUpload = LocalDateTime.now();
    }
}
