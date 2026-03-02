package com.automotive.service;

import com.automotive.dto.ParametreDTO;
import com.automotive.model.Parametre;
import com.automotive.repository.ParametreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParametreService {

    @Autowired
    private ParametreRepository parametreRepository;

    public ParametreDTO getParametreById(Long id) {
        return parametreRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public ParametreDTO getParametreByKey(String key) {
        return parametreRepository.findByCleTechnique(key)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<ParametreDTO> getAllParametres() {
        return parametreRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ParametreDTO createParametre(ParametreDTO parametreDTO) {
        Parametre parametre = convertToEntity(parametreDTO);
        Parametre savedParametre = parametreRepository.save(parametre);
        return convertToDTO(savedParametre);
    }

    public ParametreDTO updateParametre(Long id, ParametreDTO parametreDTO) {
        return parametreRepository.findById(id)
                .map(parametre -> {
                    parametre.setValeur(parametreDTO.getValeur());
                    parametre.setDescription(parametreDTO.getDescription());
                    return convertToDTO(parametreRepository.save(parametre));
                })
                .orElse(null);
    }

    public boolean deleteParametre(Long id) {
        if (parametreRepository.existsById(id)) {
            parametreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ParametreDTO convertToDTO(Parametre parametre) {
        return ParametreDTO.builder()
                .id(parametre.getId())
                .cle(parametre.getCleTechnique())
                .valeur(parametre.getValeur())
                .description(parametre.getDescription())
                .type(parametre.getType().toString())
                .build();
    }

    private Parametre convertToEntity(ParametreDTO parametreDTO) {
        return Parametre.builder()
                .cleTechnique(parametreDTO.getCle())
                .valeur(parametreDTO.getValeur())
                .description(parametreDTO.getDescription())
                .type(Parametre.Type.valueOf(parametreDTO.getType()))
                .build();
    }
}
