
package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.autolocapi.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.MaintenanceResponseDTO;
import tn.esprit.autoloc.autolocapi.service.IMaintenanceService;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO> create(
            @Valid @RequestBody MaintenanceRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(maintenanceService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(maintenanceService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDTO>> findAll() {

        return ResponseEntity.ok(maintenanceService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceRequestDTO dto) {

        return ResponseEntity.ok(maintenanceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        maintenanceService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
