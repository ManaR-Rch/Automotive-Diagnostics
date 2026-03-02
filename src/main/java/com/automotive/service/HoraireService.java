package com.automotive.service;

import com.automotive.dto.HoraireDTO;
import com.automotive.model.Horaire;
import com.automotive.repository.HoraireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoraireService {

    @Autowired
    private HoraireRepository horaireRepository;

    public HoraireDTO getHoraireById(Long id) {
        return horaireRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public HoraireDTO getHoraireByJour(String jour) {
        return horaireRepository.findByJourSemaine(Horaire.JourSemaine.valueOf(jour))
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<HoraireDTO> getAllHoraires() {
        return horaireRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HoraireDTO createHoraire(HoraireDTO horaireDTO) {
        Horaire horaire = convertToEntity(horaireDTO);
        Horaire savedHoraire = horaireRepository.save(horaire);
        return convertToDTO(savedHoraire);
    }

    public HoraireDTO updateHoraire(Long id, HoraireDTO horaireDTO) {
        return horaireRepository.findById(id)
                .map(horaire -> {
                    horaire.setHeureOuverture(horaireDTO.getHeureOuverture());
                    horaire.setHeureFermeture(horaireDTO.getHeureFermeture());
                    horaire.setEstFerme(horaireDTO.getEstFerme());
                    return convertToDTO(horaireRepository.save(horaire));
                })
                .orElse(null);
    }

    public boolean deleteHoraire(Long id) {
        if (horaireRepository.existsById(id)) {
            horaireRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private HoraireDTO convertToDTO(Horaire horaire) {
        return HoraireDTO.builder()
                .id(horaire.getId())
                .jourSemaine(horaire.getJourSemaine().toString())
                .heureOuverture(horaire.getHeureOuverture())
                .heureFermeture(horaire.getHeureFermeture())
                .estFerme(horaire.getEstFerme())
                .build();
    }

    private Horaire convertToEntity(HoraireDTO horaireDTO) {
        return Horaire.builder()
                .jourSemaine(Horaire.JourSemaine.valueOf(horaireDTO.getJourSemaine()))
                .heureOuverture(horaireDTO.getHeureOuverture())
                .heureFermeture(horaireDTO.getHeureFermeture())
                .estFerme(horaireDTO.getEstFerme())
                .build();
    }
}
