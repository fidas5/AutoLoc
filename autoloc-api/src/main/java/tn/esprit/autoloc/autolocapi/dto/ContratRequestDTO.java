package tn.esprit.autoloc.autolocapi.dto;

import jakarta.validation.constraints.DecimalMin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContratRequestDTO {

    private LocalDate dateSignature;

    @DecimalMin(
            value = "0.0",
            message = "Le montant total du contrat ne peut pas être négatif"
    )
    private BigDecimal montantTotal;

    private boolean valide;
}
