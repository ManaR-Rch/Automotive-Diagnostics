package com.automotive.service;

import com.automotive.dto.NotificationDTO;
import com.automotive.model.Notification;
import com.automotive.model.User;
import com.automotive.repository.NotificationRepository;
import com.automotive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public NotificationDTO getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<NotificationDTO> getNotificationsByUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> notificationRepository.findByUser(user)
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<NotificationDTO> getUnreadNotifications(Long userId) {
        return userRepository.findById(userId)
                .map(user -> notificationRepository.findByUserAndLueFalse(user)
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public long countUnreadNotifications(Long userId) {
        return userRepository.findById(userId)
                .map(user -> notificationRepository.countByUserAndLueFalse(user))
                .orElse(0L);
    }

    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        User user = userRepository.findById(notificationDTO.getUserId()).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Notification notification = convertToEntity(notificationDTO);
        notification.setUser(user);
        notification.setLue(false);

        Notification savedNotification = notificationRepository.save(notification);
        return convertToDTO(savedNotification);
    }

    public NotificationDTO markAsRead(Long id) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notification.setLue(true);
                    notification.setDateLecture(LocalDateTime.now());
                    return convertToDTO(notificationRepository.save(notification));
                })
                .orElse(null);
    }

    public void markAllAsRead(Long userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    List<Notification> unreadNotifications = notificationRepository.findByUserAndLueFalse(user);
                    unreadNotifications.forEach(notification -> {
                        notification.setLue(true);
                        notification.setDateLecture(LocalDateTime.now());
                    });
                    notificationRepository.saveAll(unreadNotifications);
                });
    }

    public boolean deleteNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private NotificationDTO convertToDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .userId(notification.getUser().getId())
                .type(notification.getType().toString())
                .titre(notification.getTitre())
                .message(notification.getMessage())
                .lue(notification.getLue())
                .dateCreation(notification.getDateCreation())
                .dateLecture(notification.getDateLecture())
                .build();
    }

    private Notification convertToEntity(NotificationDTO notificationDTO) {
        return Notification.builder()
                .type(Notification.Type.valueOf(notificationDTO.getType()))
                .titre(notificationDTO.getTitre())
                .message(notificationDTO.getMessage())
                .build();
    }
}
