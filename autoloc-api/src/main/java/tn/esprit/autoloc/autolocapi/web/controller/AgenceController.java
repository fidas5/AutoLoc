
package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.autolocapi.dto.AgenceRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.AgenceResponseDTO;
import tn.esprit.autoloc.autolocapi.service.IAgenceService;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;

    @PostMapping
    public ResponseEntity<AgenceResponseDTO> create(
            @Valid @RequestBody AgenceRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agenceService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenceResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(agenceService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AgenceResponseDTO>> findAll() {

        return ResponseEntity.ok(agenceService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgenceResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AgenceRequestDTO dto) {

        return ResponseEntity.ok(agenceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        agenceService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
