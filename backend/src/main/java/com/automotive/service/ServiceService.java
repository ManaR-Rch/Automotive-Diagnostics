package com.automotive.service;

import com.automotive.dto.ServiceDTO;
import com.automotive.model.Service;
import com.automotive.enums.Categorie;
import com.automotive.repository.RendezVousRepository;
import com.automotive.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    public enum DeleteResult {
        DELETED,
        DEACTIVATED,
        NOT_FOUND
    }

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    public ServiceDTO getServiceById(Long id) {
        return serviceRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ServiceDTO> getAllActiveServices() {
        return serviceRepository.findByActifTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ServiceDTO> getServicesByCategorie(String categorie) {
        return serviceRepository.findByCategorie(Categorie.valueOf(categorie))
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
                    String categorie = serviceDTO.getCategorie();
                    if (categorie != null && !categorie.isBlank()) {
                        service.setCategorie(Categorie.valueOf(categorie.trim().toUpperCase()));
                    }
                    service.setDureeEstimee(serviceDTO.getDureeEstimee());
                    service.setPrixMin(serviceDTO.getPrixMin());
                    service.setPrixMax(serviceDTO.getPrixMax());
                    service.setOrdreAffichage(serviceDTO.getOrdreAffichage());
                    return convertToDTO(serviceRepository.save(service));
                })
                .orElse(null);
    }

    public ServiceDTO toggleServiceStatus(Long id) {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setActif(!service.getActif());
                    return convertToDTO(serviceRepository.save(service));
                })
                .orElse(null);
    }

    public DeleteResult deleteService(Long id) {
        return serviceRepository.findById(id)
                .map(service -> {
                    if (rendezVousRepository.existsByService_Id(id)) {
                        service.setActif(false);
                        serviceRepository.save(service);
                        return DeleteResult.DEACTIVATED;
                    }

                    serviceRepository.deleteById(id);
                    return DeleteResult.DELETED;
                })
                .orElse(DeleteResult.NOT_FOUND);
    }

    public long countServices() {
        return serviceRepository.count();
    }

    public long countActiveServices() {
        return serviceRepository.countByActifTrue();
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
        String categorie = serviceDTO.getCategorie();
        if (categorie == null) {
            throw new IllegalArgumentException("Categorie ne peut pas etre null");
        }
        
        return Service.builder()
                .nom(serviceDTO.getNom())
                .description(serviceDTO.getDescription())
                .categorie(Categorie.valueOf(categorie.trim().toUpperCase()))
                .dureeEstimee(serviceDTO.getDureeEstimee())
                .prixMin(serviceDTO.getPrixMin())
                .prixMax(serviceDTO.getPrixMax())
                .ordreAffichage(serviceDTO.getOrdreAffichage())
                .build();
    }
}
