package com.automotive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "devis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "description_demande", columnDefinition = "TEXT")
    private String descriptionDemande;

    @Enumerated(EnumType.STRING)
    @Column(name = "delai_souhaite")
    private DelaiSouhaite delaiSouhaite;

    @Column(name = "pieces_origine")
    private Boolean piecesOrigine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut;

    @Column(name = "montant_main_oeuvre")
    private Double montantMainOeuvre;

    @Column(name = "montant_pieces")
    private Double montantPieces;

    @Column(name = "montant_fournitures")
    private Double montantFournitures;

    @Column(name = "montant_ht")
    private Double montantHt;

    @Column(name = "montant_tva")
    private Double montantTva;

    @Column(name = "montant_ttc")
    private Double montantTtc;

    @Column(name = "commentaire_admin", columnDefinition = "TEXT")
    private String commentaireAdmin;

    @Column(name = "commentaire_client", columnDefinition = "TEXT")
    private String commentaireClient;

    @Column(name = "est_automatique")
    private Boolean estAutomatique;

    @Column(name = "date_demande")
    private LocalDateTime dateDemande;

    @Column(name = "date_evaluation")
    private LocalDateTime dateEvaluation;

    @Column(name = "date_expiration")
    private LocalDateTime dateExpiration;

    @Column(name = "pdf_path")
    private String pdfPath;

    @OneToMany(mappedBy = "devis", cascade = CascadeType.ALL, fetch = jakarta.persistence.FetchType.LAZY)
    private Set<PhotoDevis> photos;

    @PrePersist
    protected void onCreate() {
        dateDemande = LocalDateTime.now();
    }

    public enum DelaiSouhaite {
        SOUS_48H, CETTE_SEMAINE, FLEXIBLE
    }

    public enum Statut {
        EN_ATTENTE, EVALUE, VALIDE, REFUSE, EXPIRE
    }
}
