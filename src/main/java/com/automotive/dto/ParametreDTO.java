package com.automotive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParametreDTO {
    private Long id;
    private String cle;
    private String valeur;
    private String description;
    private String type;
}
