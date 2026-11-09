
package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.autolocapi.dto.ClientRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ClientResponseDTO;
import tn.esprit.autoloc.autolocapi.service.IClientService;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(
            @Valid @RequestBody ClientRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(clientService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> findAll() {

        return ResponseEntity.ok(clientService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequestDTO dto) {

        return ResponseEntity.ok(clientService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        clientService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
