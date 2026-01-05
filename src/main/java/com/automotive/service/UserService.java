package com.automotive.service;

import com.automotive.dto.UserDTO;
import com.automotive.model.User;
import com.automotive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.existsByTelephone(userDTO.getTelephone())) {
            throw new IllegalArgumentException("Telephone already exists");
        }

        User user = convertToEntity(userDTO);
        user.setRole(User.Role.CLIENT);
        user.setActif(true);

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setNom(userDTO.getNom());
                    user.setPrenom(userDTO.getPrenom());
                    user.setTelephone(userDTO.getTelephone());
                    return convertToDTO(userRepository.save(user));
                })
                .orElse(null);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .role(user.getRole().toString())
                .dateInscription(user.getDateInscription())
                .actif(user.getActif())
                .build();
    }

    private User convertToEntity(UserDTO userDTO) {
        return User.builder()
                .nom(userDTO.getNom())
                .prenom(userDTO.getPrenom())
                .email(userDTO.getEmail())
                .telephone(userDTO.getTelephone())
                .motDePasse(userDTO.getEmail()) // À hasher avec BCrypt en production
                .build();
    }
}
