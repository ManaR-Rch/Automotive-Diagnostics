package com.automotive.service;

import com.automotive.dto.ServiceDTO;
import com.automotive.model.Service;
import com.automotive.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public ServiceDTO getServiceById(Long id) {
        return serviceRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<ServiceDTO> getAllActiveServices() {
        return serviceRepository.findByActifTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ServiceDTO> getServicesByCategorie(String categorie) {
        return serviceRepository.findByCategorie(Service.Categorie.valueOf(categorie))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ServiceDTO createService(ServiceDTO serviceDTO) {
        Service service = convertToEntity(serviceDTO);
        service.setActif(true);
        Service savedService = serviceRepository.save(service);
        return convertToDTO(savedService);
    }

    public ServiceDTO updateService(Long id, ServiceDTO serviceDTO) {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setNom(serviceDTO.getNom());
                    service.setDescription(serviceDTO.getDescription());
                    service.setCategorie(Service.Categorie.valueOf(serviceDTO.getCategorie()));
                    service.setDureeEstimee(serviceDTO.getDureeEstimee());
                    service.setPrixMin(serviceDTO.getPrixMin());
                    service.setPrixMax(serviceDTO.getPrixMax());
                    return convertToDTO(serviceRepository.save(service));
                })
                .orElse(null);
    }

    public boolean deleteService(Long id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ServiceDTO convertToDTO(Service service) {
        return ServiceDTO.builder()
                .id(service.getId())
                .nom(service.getNom())
                .description(service.getDescription())
                .categorie(service.getCategorie().toString())
                .dureeEstimee(service.getDureeEstimee())
                .prixMin(service.getPrixMin())
                .prixMax(service.getPrixMax())
                .actif(service.getActif())
                .ordreAffichage(service.getOrdreAffichage())
                .build();
    }

    private Service convertToEntity(ServiceDTO serviceDTO) {
        return Service.builder()
                .nom(serviceDTO.getNom())
                .description(serviceDTO.getDescription())
                .categorie(Service.Categorie.valueOf(serviceDTO.getCategorie()))
                .dureeEstimee(serviceDTO.getDureeEstimee())
                .prixMin(serviceDTO.getPrixMin())
                .prixMax(serviceDTO.getPrixMax())
                .ordreAffichage(serviceDTO.getOrdreAffichage())
                .build();
    }
}
