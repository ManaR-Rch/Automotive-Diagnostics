package com.automotive.service;

import com.automotive.dto.CreneauDTO;
import com.automotive.model.Creneau;
import com.automotive.model.RendezVous;
import com.automotive.repository.CreneauRepository;
import com.automotive.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreneauService {

    @Autowired
    private CreneauRepository creneauRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    public CreneauDTO getCreneauById(Long id) {
        return creneauRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<CreneauDTO> getCreneauxDisponibles() {
        return creneauRepository.findByDisponibleTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CreneauDTO> getCreneauxByDateRange(LocalDateTime start, LocalDateTime end) {
        return creneauRepository.findByDateCreneauBetween(start, end)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CreneauDTO createCreneau(CreneauDTO creneauDTO) {
        Creneau creneau = convertToEntity(creneauDTO);
        creneau.setDisponible(true);
        Creneau savedCreneau = creneauRepository.save(creneau);
        return convertToDTO(savedCreneau);
    }

    public CreneauDTO updateCreneau(Long id, CreneauDTO creneauDTO) {
        return creneauRepository.findById(id)
                .map(creneau -> {
                    creneau.setDateCreneau(creneauDTO.getDateCreneau());
                    creneau.setHeureDebut(creneauDTO.getHeureDebut());
                    creneau.setHeureFin(creneauDTO.getHeureFin());
                    return convertToDTO(creneauRepository.save(creneau));
                })
                .orElse(null);
    }

    public boolean deleteCreneau(Long id) {
        if (creneauRepository.existsById(id)) {
            creneauRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private CreneauDTO convertToDTO(Creneau creneau) {
        return CreneauDTO.builder()
                .id(creneau.getId())
                .dateCreneau(creneau.getDateCreneau())
                .heureDebut(creneau.getHeureDebut())
                .heureFin(creneau.getHeureFin())
                .disponible(creneau.getDisponible())
                .rdvId(creneau.getRendezVous() != null ? creneau.getRendezVous().getId() : null)
                .build();
    }

    private Creneau convertToEntity(CreneauDTO creneauDTO) {
        return Creneau.builder()
                .dateCreneau(creneauDTO.getDateCreneau())
                .heureDebut(creneauDTO.getHeureDebut())
                .heureFin(creneauDTO.getHeureFin())
                .build();
    }
}
