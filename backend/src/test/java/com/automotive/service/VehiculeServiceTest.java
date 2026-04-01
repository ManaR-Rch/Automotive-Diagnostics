package com.automotive.service;

import com.automotive.dto.VehiculeDTO;
import com.automotive.model.User;
import com.automotive.model.Vehicule;
import com.automotive.enums.Carburant;
import com.automotive.repository.UserRepository;
import com.automotive.repository.VehiculeRepository;
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
class VehiculeServiceTest {

    @Mock
    private VehiculeRepository vehiculeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VehiculeService vehiculeService;

    private Vehicule sampleVehicule;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = User.builder().id(1L).build();
        sampleVehicule = Vehicule.builder()
                .id(1L)
                .user(sampleUser)
                .marque("Toyota")
                .modele("Corolla")
                .annee(2020)
                .carburant(Carburant.ESSENCE)
                .kilometrage(50000)
                .vin("VIN123")
                .estPrincipal(true)
                .build();
    }

    @Test
    void testGetVehiculeById_Success() {
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(sampleVehicule));

        VehiculeDTO result = vehiculeService.getVehiculeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Toyota", result.getMarque());
        verify(vehiculeRepository, times(1)).findById(1L);
    }
}