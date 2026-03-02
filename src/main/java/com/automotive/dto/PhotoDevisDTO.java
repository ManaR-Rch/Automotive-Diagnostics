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
public class PhotoDevisDTO {
    private Long id;
    private Long devisId;
    private String nomFichier;
    private String cheminFichier;
    private Long taille;
    private LocalDateTime dateUpload;
}
