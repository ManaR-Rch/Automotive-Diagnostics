package com.automotive.service;

import com.automotive.dto.*;
import com.automotive.model.User;
import com.automotive.repository.UserRepository;
import com.automotive.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ==================== AUTHENTIFICATION ====================

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé");
        }
        if (userRepository.existsByTelephone(request.getTelephone())) {
            throw new IllegalArgumentException("Ce téléphone est déjà utilisé");
        }

        User user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .role(User.Role.CLIENT)
                .actif(true)
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().toString());

        return AuthResponse.builder()
                .token(token)
                .id(savedUser.getId())
                .nom(savedUser.getNom())
                .prenom(savedUser.getPrenom())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().toString())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
            throw new IllegalArgumentException("Email ou mot de passe incorrect");
        }

        if (!user.getActif()) {
            throw new IllegalArgumentException("Compte désactivé");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());

        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }

    // ==================== PROFIL UTILISATEUR ====================

    public UserDTO getProfile(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public UserDTO updateProfile(String email, UpdateProfileRequest request) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    user.setNom(request.getNom());
                    user.setPrenom(request.getPrenom());
                    user.setTelephone(request.getTelephone());
                    return convertToDTO(userRepository.save(user));
                })
                .orElse(null);
    }

    public boolean changePassword(String email, ChangePasswordRequest request) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    if (!passwordEncoder.matches(request.getAncienMotDePasse(), user.getMotDePasse())) {
                        throw new IllegalArgumentException("Ancien mot de passe incorrect");
                    }
                    user.setMotDePasse(passwordEncoder.encode(request.getNouveauMotDePasse()));
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    // ==================== CRUD UTILISATEURS (ADMIN) ====================

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

    public List<UserDTO> getAllClients() {
        return userRepository.findByRole(User.Role.CLIENT)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDTO, String password) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.existsByTelephone(userDTO.getTelephone())) {
            throw new IllegalArgumentException("Telephone already exists");
        }

        User user = User.builder()
                .nom(userDTO.getNom())
                .prenom(userDTO.getPrenom())
                .email(userDTO.getEmail())
                .telephone(userDTO.getTelephone())
                .motDePasse(passwordEncoder.encode(password))
                .role(User.Role.valueOf(userDTO.getRole()))
                .actif(true)
                .build();

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setNom(userDTO.getNom());
                    user.setPrenom(userDTO.getPrenom());
                    user.setTelephone(userDTO.getTelephone());
                    if (userDTO.getRole() != null) {
                        user.setRole(User.Role.valueOf(userDTO.getRole()));
                    }
                    return convertToDTO(userRepository.save(user));
                })
                .orElse(null);
    }

    public UserDTO toggleUserStatus(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setActif(!user.getActif());
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

    public long countUsers() {
        return userRepository.count();
    }

    public long countClients() {
        return userRepository.countByRole(User.Role.CLIENT);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
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
}
