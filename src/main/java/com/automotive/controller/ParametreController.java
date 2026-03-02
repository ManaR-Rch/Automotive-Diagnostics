package com.automotive.controller;

import com.automotive.dto.ParametreDTO;
import com.automotive.service.ParametreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/parametres")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParametreController {

    @Autowired
    private ParametreService parametreService;

    @GetMapping("/{id}")
    public ResponseEntity<ParametreDTO> getParametreById(@PathVariable Long id) {
        ParametreDTO parametre = parametreService.getParametreById(id);
        return parametre != null ? ResponseEntity.ok(parametre) : ResponseEntity.notFound().build();
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<ParametreDTO> getParametreByKey(@PathVariable String key) {
        ParametreDTO parametre = parametreService.getParametreByKey(key);
        return parametre != null ? ResponseEntity.ok(parametre) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ParametreDTO>> getAllParametres() {
        List<ParametreDTO> parametres = parametreService.getAllParametres();
        return ResponseEntity.ok(parametres);
    }

    @PostMapping
    public ResponseEntity<ParametreDTO> createParametre(@RequestBody ParametreDTO parametreDTO) {
        ParametreDTO createdParametre = parametreService.createParametre(parametreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParametre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParametreDTO> updateParametre(@PathVariable Long id, @RequestBody ParametreDTO parametreDTO) {
        ParametreDTO updatedParametre = parametreService.updateParametre(id, parametreDTO);
        return updatedParametre != null ? ResponseEntity.ok(updatedParametre) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParametre(@PathVariable Long id) {
        return parametreService.deleteParametre(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
