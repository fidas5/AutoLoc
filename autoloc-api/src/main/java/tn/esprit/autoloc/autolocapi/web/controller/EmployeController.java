
package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.autolocapi.dto.EmployeRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.EmployeResponseDTO;
import tn.esprit.autoloc.autolocapi.service.IEmployeService;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @PostMapping
    public ResponseEntity<EmployeResponseDTO> create(
            @Valid @RequestBody EmployeRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(employeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeResponseDTO>> findAll() {

        return ResponseEntity.ok(employeService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeRequestDTO dto) {

        return ResponseEntity.ok(employeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        employeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
