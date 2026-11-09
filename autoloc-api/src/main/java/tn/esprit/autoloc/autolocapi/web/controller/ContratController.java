
package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.List;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.autolocapi.dto.ContratRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ContratResponseDTO;
import tn.esprit.autoloc.autolocapi.service.IContratService;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Tag(
        name = "Contrats",
        description = "Gestion des contrats de location"
)
public class ContratController {

    private final IContratService contratService;

    // CREATE
    @PostMapping
    @Operation(
            summary = "Créer un contrat",
            description = "Crée un nouveau contrat de location."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Contrat créé avec succès"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données du contrat invalides"
            )
    })
    public ResponseEntity<ContratResponseDTO> create(
            @Valid @RequestBody ContratRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contratService.create(dto));
    }

    // READ ALL
    @GetMapping
    @Operation(
            summary = "Lister les contrats",
            description = "Retourne la liste de tous les contrats."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des contrats récupérée"
    )
    public ResponseEntity<List<ContratResponseDTO>> findAll() {

        return ResponseEntity.ok(contratService.findAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Consulter un contrat",
            description = "Retourne un contrat à partir de son identifiant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contrat trouvé"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contrat introuvable"
            )
    })
    public ResponseEntity<ContratResponseDTO> findById(
            @Parameter(
                    description = "Identifiant du contrat",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(contratService.findById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    @Operation(
            summary = "Modifier un contrat",
            description = "Met à jour un contrat existant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contrat modifié avec succès"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données du contrat invalides"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contrat introuvable"
            )
    })
    public ResponseEntity<ContratResponseDTO> update(
            @Parameter(
                    description = "Identifiant du contrat à modifier",
                    example = "1"
            )
            @PathVariable Long id,
            @Valid @RequestBody ContratRequestDTO dto) {

        return ResponseEntity.ok(contratService.update(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer un contrat",
            description = "Supprime un contrat existant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Contrat supprimé avec succès"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contrat introuvable"
            )
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(
                    description = "Identifiant du contrat à supprimer",
                    example = "1"
            )
            @PathVariable Long id) {

        contratService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
