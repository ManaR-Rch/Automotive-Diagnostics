package com.automotive.controller;

import com.automotive.dto.PhotoDevisDTO;
import com.automotive.service.PhotoDevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/photos-devis")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PhotoDevisController {

    @Autowired
    private PhotoDevisService photoDevisService;

    @GetMapping("/{id}")
    public ResponseEntity<PhotoDevisDTO> getPhotoDevisById(@PathVariable Long id) {
        PhotoDevisDTO photoDevis = photoDevisService.getPhotoDevisById(id);
        return photoDevis != null ? ResponseEntity.ok(photoDevis) : ResponseEntity.notFound().build();
    }

    @GetMapping("/devis/{devisId}")
    public ResponseEntity<List<PhotoDevisDTO>> getPhotosByDevis(@PathVariable Long devisId) {
        List<PhotoDevisDTO> photos = photoDevisService.getPhotosByDevis(devisId);
        return ResponseEntity.ok(photos);
    }

    @PostMapping
    public ResponseEntity<PhotoDevisDTO> createPhotoDevis(@RequestBody PhotoDevisDTO photoDevisDTO) {
        try {
            PhotoDevisDTO createdPhotoDevis = photoDevisService.createPhotoDevis(photoDevisDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPhotoDevis);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhotoDevisDTO> updatePhotoDevis(@PathVariable Long id, @RequestBody PhotoDevisDTO photoDevisDTO) {
        PhotoDevisDTO updatedPhotoDevis = photoDevisService.updatePhotoDevis(id, photoDevisDTO);
        return updatedPhotoDevis != null ? ResponseEntity.ok(updatedPhotoDevis) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhotoDevis(@PathVariable Long id) {
        return photoDevisService.deletePhotoDevis(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
