package com.automotive.service;

import com.automotive.dto.ServiceDTO;
import com.automotive.model.Service;
import com.automotive.enums.Categorie;
import com.automotive.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceService serviceService;

    private Service sampleService;

    @BeforeEach
    void setUp() {
        sampleService = Service.builder()
                .id(1L)
                .nom("Vidange")
                .description("Changement huile")
                .categorie(Categorie.MAINTENANCE)
                .dureeEstimee(60)
                .prixMin(50.0)
                .prixMax(100.0)
                .actif(true)
                .ordreAffichage(1)
                .build();
    }

    @Test
    void testGetServiceById_Success() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(sampleService));

        ServiceDTO result = serviceService.getServiceById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Vidange", result.getNom());
        assertEquals(Categorie.MAINTENANCE.name(), result.getCategorie());
        verify(serviceRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllActiveServices_Success() {
        when(serviceRepository.findByActifTrue()).thenReturn(Arrays.asList(sampleService));

        List<ServiceDTO> results = serviceService.getAllActiveServices();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Vidange", results.get(0).getNom());
        verify(serviceRepository, times(1)).findByActifTrue();
    }
}