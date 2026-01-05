package com.automotive.controller;

import com.automotive.dto.ServiceDTO;
import com.automotive.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Long id) {
        ServiceDTO service = serviceService.getServiceById(id);
        return service != null ? ResponseEntity.ok(service) : ResponseEntity.notFound().build();
    }

    @GetMapping("/actifs")
    public ResponseEntity<List<ServiceDTO>> getAllActiveServices() {
        List<ServiceDTO> services = serviceService.getAllActiveServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<ServiceDTO>> getServicesByCategorie(@PathVariable String categorie) {
        List<ServiceDTO> services = serviceService.getServicesByCategorie(categorie);
        return ResponseEntity.ok(services);
    }

    @PostMapping
    public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO) {
        ServiceDTO createdService = serviceService.createService(serviceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable Long id, @RequestBody ServiceDTO serviceDTO) {
        ServiceDTO updatedService = serviceService.updateService(id, serviceDTO);
        return updatedService != null ? ResponseEntity.ok(updatedService) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        return serviceService.deleteService(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
