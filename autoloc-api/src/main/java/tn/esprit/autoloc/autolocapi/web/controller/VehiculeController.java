
package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.autolocapi.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.VehiculeResponseDTO;
import tn.esprit.autoloc.autolocapi.service.IVehiculeService;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @PostMapping
    public ResponseEntity<VehiculeResponseDTO> create(
            @Valid @RequestBody VehiculeRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehiculeService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(vehiculeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<VehiculeResponseDTO>> findAll() {

        return ResponseEntity.ok(vehiculeService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody VehiculeRequestDTO dto) {

        return ResponseEntity.ok(vehiculeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        vehiculeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
