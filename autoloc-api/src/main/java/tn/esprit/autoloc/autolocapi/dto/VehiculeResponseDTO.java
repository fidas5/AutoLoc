
package tn.esprit.autoloc.autolocapi.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeResponseDTO {

    private Long id;

    private String immatriculation;

    private String marque;

    private String modele;

    private BigDecimal tarifJournalier;

    private CategorieVehicule categorie;

    private StatutVehicule statut;

    private Long agenceId;
}
