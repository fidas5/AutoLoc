
package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.autolocapi.dto.ReservationRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ReservationResponseDTO;
import tn.esprit.autoloc.autolocapi.service.IReservationService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(
            @Valid @RequestBody ReservationRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(reservationService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> findAll() {

        return ResponseEntity.ok(reservationService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequestDTO dto) {

        return ResponseEntity.ok(reservationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        reservationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
