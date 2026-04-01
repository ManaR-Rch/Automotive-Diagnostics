package com.automotive.service;

import com.automotive.dto.VehiculeDTO;
import com.automotive.model.Vehicule;
import com.automotive.enums.Carburant;
import com.automotive.model.User;
import com.automotive.repository.VehiculeRepository;
import com.automotive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private UserRepository userRepository;

    public VehiculeDTO getVehiculeById(Long id) {
        return vehiculeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<VehiculeDTO> getVehiculesByUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> vehiculeRepository.findByUser(user)
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public VehiculeDTO createVehicule(VehiculeDTO vehiculeDTO) {
        return userRepository.findById(vehiculeDTO.getUserId())
                .map(user -> {
                    Vehicule vehicule = convertToEntity(vehiculeDTO);
                    vehicule.setUser(user);
                    Vehicule savedVehicule = vehiculeRepository.save(vehicule);
                    return convertToDTO(savedVehicule);
                })
                .orElse(null);
    }

    public VehiculeDTO updateVehicule(Long id, VehiculeDTO vehiculeDTO) {
        return vehiculeRepository.findById(id)
                .map(vehicule -> {
                    vehicule.setMarque(vehiculeDTO.getMarque());
                    vehicule.setModele(vehiculeDTO.getModele());
                    vehicule.setAnnee(vehiculeDTO.getAnnee());
                    vehicule.setKilometrage(vehiculeDTO.getKilometrage());
                    return convertToDTO(vehiculeRepository.save(vehicule));
                })
                .orElse(null);
    }

    public boolean deleteVehicule(Long id) {
        if (vehiculeRepository.existsById(id)) {
            vehiculeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private VehiculeDTO convertToDTO(Vehicule vehicule) {
        return VehiculeDTO.builder()
                .id(vehicule.getId())
                .userId(vehicule.getUser().getId())
                .marque(vehicule.getMarque())
                .modele(vehicule.getModele())
                .annee(vehicule.getAnnee())
                .carburant(vehicule.getCarburant().toString())
                .kilometrage(vehicule.getKilometrage())
                .vin(vehicule.getVin())
                .estPrincipal(vehicule.getEstPrincipal())
                .build();
    }

    private Vehicule convertToEntity(VehiculeDTO vehiculeDTO) {
        return Vehicule.builder()
                .marque(vehiculeDTO.getMarque())
                .modele(vehiculeDTO.getModele())
                .annee(vehiculeDTO.getAnnee())
                .carburant(Carburant.valueOf(vehiculeDTO.getCarburant()))
                .kilometrage(vehiculeDTO.getKilometrage())
                .vin(vehiculeDTO.getVin())
                .estPrincipal(vehiculeDTO.getEstPrincipal())
                .build();
    }
}
