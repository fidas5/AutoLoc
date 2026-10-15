package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.service.IMaintenanceService;


@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<Maintenance> create(
            @RequestBody Maintenance maintenance) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(maintenanceService.create(maintenance));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> findById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                maintenanceService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Maintenance>> findAll() {
        return ResponseEntity.ok(
                maintenanceService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> update(
            @PathVariable Long id,
            @RequestBody Maintenance maintenance) {
        return ResponseEntity.ok(
                maintenanceService.update(id, maintenance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        maintenanceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
