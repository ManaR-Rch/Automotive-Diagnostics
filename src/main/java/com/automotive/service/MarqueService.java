package com.automotive.service;

import com.automotive.dto.MarqueDTO;
import com.automotive.model.Marque;
import com.automotive.repository.MarqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarqueService {

    @Autowired
    private MarqueRepository marqueRepository;

    public MarqueDTO getMarqueById(Long id) {
        return marqueRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public MarqueDTO getMarqueByNom(String nom) {
        return marqueRepository.findByNom(nom)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<MarqueDTO> getAllActiveMarques() {
        return marqueRepository.findByActifTrueOrderByOrdreAffichageAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MarqueDTO> getPartnerMarques() {
        return marqueRepository.findByEstPartenaireTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MarqueDTO createMarque(MarqueDTO marqueDTO) {
        Marque marque = convertToEntity(marqueDTO);
        marque.setActif(true);
        Marque savedMarque = marqueRepository.save(marque);
        return convertToDTO(savedMarque);
    }

    public MarqueDTO updateMarque(Long id, MarqueDTO marqueDTO) {
        return marqueRepository.findById(id)
                .map(marque -> {
                    marque.setNom(marqueDTO.getNom());
                    marque.setLogoPath(marqueDTO.getLogoPath());
                    marque.setEstPartenaire(marqueDTO.getEstPartenaire());
                    marque.setOrdreAffichage(marqueDTO.getOrdreAffichage());
                    return convertToDTO(marqueRepository.save(marque));
                })
                .orElse(null);
    }

    public boolean deleteMarque(Long id) {
        if (marqueRepository.existsById(id)) {
            marqueRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private MarqueDTO convertToDTO(Marque marque) {
        return MarqueDTO.builder()
                .id(marque.getId())
                .nom(marque.getNom())
                .logoPath(marque.getLogoPath())
                .estPartenaire(marque.getEstPartenaire())
                .ordreAffichage(marque.getOrdreAffichage())
                .actif(marque.getActif())
                .build();
    }

    private Marque convertToEntity(MarqueDTO marqueDTO) {
        return Marque.builder()
                .nom(marqueDTO.getNom())
                .logoPath(marqueDTO.getLogoPath())
                .estPartenaire(marqueDTO.getEstPartenaire())
                .ordreAffichage(marqueDTO.getOrdreAffichage())
                .build();
    }
}
