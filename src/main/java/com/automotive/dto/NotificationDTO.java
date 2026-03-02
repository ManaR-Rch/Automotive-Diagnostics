package com.automotive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private Long userId;
    private String type;
    private String titre;
    private String message;
    private Boolean lue;
    private LocalDateTime dateCreation;
    private LocalDateTime dateLecture;
}
