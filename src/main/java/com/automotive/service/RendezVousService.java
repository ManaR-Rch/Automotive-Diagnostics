package com.automotive.service;

import com.automotive.dto.RendezVousDTO;
import com.automotive.model.*;
import com.automotive.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public RendezVousDTO getRendezVousById(Long id) {
        return rendezVousRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<RendezVousDTO> getRendezVousByUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> rendezVousRepository.findByUser(user)
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<RendezVousDTO> getRendezVousByStatut(String statut) {
        return rendezVousRepository.findByStatut(RendezVous.Statut.valueOf(statut))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RendezVousDTO createRendezVous(RendezVousDTO rendezVousDTO) {
        User user = userRepository.findById(rendezVousDTO.getUserId()).orElse(null);
        Vehicule vehicule = vehiculeRepository.findById(rendezVousDTO.getVehiculeId()).orElse(null);
        com.automotive.model.Service service = serviceRepository.findById(rendezVousDTO.getServiceId()).orElse(null);

        if (user == null || vehicule == null || service == null) {
            throw new IllegalArgumentException("User, Vehicle or Service not found");
        }

        RendezVous rendezVous = convertToEntity(rendezVousDTO);
        rendezVous.setUser(user);
        rendezVous.setVehicule(vehicule);
        rendezVous.setService(service);
        rendezVous.setStatut(RendezVous.Statut.CONFIRME);

        RendezVous savedRendezVous = rendezVousRepository.save(rendezVous);
        return convertToDTO(savedRendezVous);
    }

    public RendezVousDTO updateRendezVous(Long id, RendezVousDTO rendezVousDTO) {
        return rendezVousRepository.findById(id)
                .map(rendezVous -> {
                    rendezVous.setDateRdv(rendezVousDTO.getDateRdv());
                    rendezVous.setHeureDebut(rendezVousDTO.getHeureDebut());
                    rendezVous.setHeureFin(rendezVousDTO.getHeureFin());
                    rendezVous.setDescriptionProbleme(rendezVousDTO.getDescriptionProbleme());
                    rendezVous.setUrgence(RendezVous.Urgence.valueOf(rendezVousDTO.getUrgence()));
                    rendezVous.setNotesAdmin(rendezVousDTO.getNotesAdmin());
                    return convertToDTO(rendezVousRepository.save(rendezVous));
                })
                .orElse(null);
    }

    public RendezVousDTO updateStatut(Long id, String statut) {
        return rendezVousRepository.findById(id)
                .map(rendezVous -> {
                    rendezVous.setStatut(RendezVous.Statut.valueOf(statut));
                    return convertToDTO(rendezVousRepository.save(rendezVous));
                })
                .orElse(null);
    }

    public boolean deleteRendezVous(Long id) {
        if (rendezVousRepository.existsById(id)) {
            rendezVousRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private RendezVousDTO convertToDTO(RendezVous rendezVous) {
        return RendezVousDTO.builder()
                .id(rendezVous.getId())
                .userId(rendezVous.getUser().getId())
                .vehiculeId(rendezVous.getVehicule().getId())
                .serviceId(rendezVous.getService().getId())
                .dateRdv(rendezVous.getDateRdv())
                .heureDebut(rendezVous.getHeureDebut())
                .heureFin(rendezVous.getHeureFin())
                .descriptionProbleme(rendezVous.getDescriptionProbleme())
                .urgence(rendezVous.getUrgence().toString())
                .statut(rendezVous.getStatut().toString())
                .notesAdmin(rendezVous.getNotesAdmin())
                .dateCreation(rendezVous.getDateCreation())
                .dateModification(rendezVous.getDateModification())
                .build();
    }

    private RendezVous convertToEntity(RendezVousDTO rendezVousDTO) {
        return RendezVous.builder()
                .dateRdv(rendezVousDTO.getDateRdv())
                .heureDebut(rendezVousDTO.getHeureDebut())
                .heureFin(rendezVousDTO.getHeureFin())
                .descriptionProbleme(rendezVousDTO.getDescriptionProbleme())
                .urgence(RendezVous.Urgence.valueOf(rendezVousDTO.getUrgence()))
                .build();
    }
}
