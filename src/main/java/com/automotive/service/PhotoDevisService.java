package com.automotive.service;

import com.automotive.dto.PhotoDevisDTO;
import com.automotive.model.PhotoDevis;
import com.automotive.model.Devis;
import com.automotive.repository.PhotoDevisRepository;
import com.automotive.repository.DevisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoDevisService {

    @Autowired
    private PhotoDevisRepository photoDevisRepository;

    @Autowired
    private DevisRepository devisRepository;

    public PhotoDevisDTO getPhotoDevisById(Long id) {
        return photoDevisRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<PhotoDevisDTO> getPhotosByDevis(Long devisId) {
        return devisRepository.findById(devisId)
                .map(devis -> photoDevisRepository.findByDevis(devis)
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public PhotoDevisDTO createPhotoDevis(PhotoDevisDTO photoDevisDTO) {
        Devis devis = devisRepository.findById(photoDevisDTO.getDevisId()).orElse(null);

        if (devis == null) {
            throw new IllegalArgumentException("Devis not found");
        }

        PhotoDevis photoDevis = convertToEntity(photoDevisDTO);
        photoDevis.setDevis(devis);
        PhotoDevis savedPhotoDevis = photoDevisRepository.save(photoDevis);
        return convertToDTO(savedPhotoDevis);
    }

    public PhotoDevisDTO updatePhotoDevis(Long id, PhotoDevisDTO photoDevisDTO) {
        return photoDevisRepository.findById(id)
                .map(photoDevis -> {
                    photoDevis.setNomFichier(photoDevisDTO.getNomFichier());
                    photoDevis.setCheminFichier(photoDevisDTO.getCheminFichier());
                    photoDevis.setTaille(photoDevisDTO.getTaille());
                    return convertToDTO(photoDevisRepository.save(photoDevis));
                })
                .orElse(null);
    }

    public boolean deletePhotoDevis(Long id) {
        if (photoDevisRepository.existsById(id)) {
            photoDevisRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PhotoDevisDTO convertToDTO(PhotoDevis photoDevis) {
        return PhotoDevisDTO.builder()
                .id(photoDevis.getId())
                .devisId(photoDevis.getDevis().getId())
                .nomFichier(photoDevis.getNomFichier())
                .cheminFichier(photoDevis.getCheminFichier())
                .taille(photoDevis.getTaille())
                .dateUpload(photoDevis.getDateUpload())
                .build();
    }

    private PhotoDevis convertToEntity(PhotoDevisDTO photoDevisDTO) {
        return PhotoDevis.builder()
                .nomFichier(photoDevisDTO.getNomFichier())
                .cheminFichier(photoDevisDTO.getCheminFichier())
                .taille(photoDevisDTO.getTaille())
                .build();
    }
}
