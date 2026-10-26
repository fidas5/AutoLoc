
package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.service.IPaiementService;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Consultation des paiements des contrats")
public class PaiementController {

    private final IPaiementService paiementService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Consulter un paiement",
            description = "Retourne un paiement à partir de son identifiant."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paiement trouvé"),
            @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    })
    public ResponseEntity<Paiement> findById(
            @Parameter(description = "Identifiant du paiement", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(paiementService.findById(id));
    }

    @GetMapping
    @Operation(
            summary = "Lister les paiements",
            description = "Retourne la liste de tous les paiements."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des paiements récupérée")
    })
    public ResponseEntity<List<Paiement>> findAll() {

        return ResponseEntity.ok(paiementService.findAll());
    }
}
