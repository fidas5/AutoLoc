
package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.autolocapi.dto.EquipementRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.EquipementResponseDTO;
import tn.esprit.autoloc.autolocapi.service.IEquipementService;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;

    @PostMapping
    public ResponseEntity<EquipementResponseDTO> create(
            @Valid @RequestBody EquipementRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(equipementService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipementResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(equipementService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<EquipementResponseDTO>> findAll() {

        return ResponseEntity.ok(equipementService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipementResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EquipementRequestDTO dto) {

        return ResponseEntity.ok(equipementService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        equipementService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
