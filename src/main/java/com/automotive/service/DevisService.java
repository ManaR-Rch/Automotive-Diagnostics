package com.automotive.service;

import com.automotive.dto.DevisDTO;
import com.automotive.model.*;
import com.automotive.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevisService {

    @Autowired
    private DevisRepository devisRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public DevisDTO getDevisById(Long id) {
        return devisRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<DevisDTO> getDevisByUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> devisRepository.findByUser(user)
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<DevisDTO> getDevisByStatut(String statut) {
        return devisRepository.findByStatut(Devis.Statut.valueOf(statut))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DevisDTO createDevis(DevisDTO devisDTO) {
        User user = userRepository.findById(devisDTO.getUserId()).orElse(null);
        Vehicule vehicule = vehiculeRepository.findById(devisDTO.getVehiculeId()).orElse(null);

        if (user == null || vehicule == null) {
            throw new IllegalArgumentException("User or Vehicle not found");
        }

        Devis devis = convertToEntity(devisDTO);
        devis.setUser(user);
        devis.setVehicule(vehicule);
        devis.setStatut(Devis.Statut.EN_ATTENTE);
        devis.setEstAutomatique(false);

        if (devisDTO.getServiceId() != null) {
            com.automotive.model.Service service = serviceRepository.findById(devisDTO.getServiceId()).orElse(null);
            devis.setService(service);
        }

        Devis savedDevis = devisRepository.save(devis);
        return convertToDTO(savedDevis);
    }

    public DevisDTO updateDevisStatut(Long id, String statut) {
        return devisRepository.findById(id)
                .map(devis -> {
                    devis.setStatut(Devis.Statut.valueOf(statut));
                    if (statut.equals("EVALUE")) {
                        devis.setDateEvaluation(LocalDateTime.now());
                    }
                    return convertToDTO(devisRepository.save(devis));
                })
                .orElse(null);
    }

    public DevisDTO updateDevisMontants(Long id, DevisDTO devisDTO) {
        return devisRepository.findById(id)
                .map(devis -> {
                    devis.setMontantMainOeuvre(devisDTO.getMontantMainOeuvre());
                    devis.setMontantPieces(devisDTO.getMontantPieces());
                    devis.setMontantFournitures(devisDTO.getMontantFournitures());
                    devis.setMontantHt(devisDTO.getMontantHt());
                    devis.setMontantTva(devisDTO.getMontantTva());
                    devis.setMontantTtc(devisDTO.getMontantTtc());
                    devis.setCommentaireAdmin(devisDTO.getCommentaireAdmin());
                    return convertToDTO(devisRepository.save(devis));
                })
                .orElse(null);
    }

    public long countDevisEnAttente() {
        return devisRepository.countByStatutEnAttente();
    }

    private DevisDTO convertToDTO(Devis devis) {
        return DevisDTO.builder()
                .id(devis.getId())
                .userId(devis.getUser().getId())
                .vehiculeId(devis.getVehicule().getId())
                .serviceId(devis.getService() != null ? devis.getService().getId() : null)
                .descriptionDemande(devis.getDescriptionDemande())
                .delaiSouhaite(devis.getDelaiSouhaite() != null ? devis.getDelaiSouhaite().toString() : null)
                .piecesOrigine(devis.getPiecesOrigine())
                .statut(devis.getStatut().toString())
                .montantMainOeuvre(devis.getMontantMainOeuvre())
                .montantPieces(devis.getMontantPieces())
                .montantFournitures(devis.getMontantFournitures())
                .montantHt(devis.getMontantHt())
                .montantTva(devis.getMontantTva())
                .montantTtc(devis.getMontantTtc())
                .commentaireAdmin(devis.getCommentaireAdmin())
                .commentaireClient(devis.getCommentaireClient())
                .estAutomatique(devis.getEstAutomatique())
                .dateDemande(devis.getDateDemande())
                .dateEvaluation(devis.getDateEvaluation())
                .dateExpiration(devis.getDateExpiration())
                .pdfPath(devis.getPdfPath())
                .build();
    }

    private Devis convertToEntity(DevisDTO devisDTO) {
        return Devis.builder()
                .descriptionDemande(devisDTO.getDescriptionDemande())
                .delaiSouhaite(devisDTO.getDelaiSouhaite() != null ? Devis.DelaiSouhaite.valueOf(devisDTO.getDelaiSouhaite()) : null)
                .piecesOrigine(devisDTO.getPiecesOrigine())
                .build();
    }
}
