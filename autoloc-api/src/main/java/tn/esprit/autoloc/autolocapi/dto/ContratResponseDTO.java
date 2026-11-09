package tn.esprit.autoloc.autolocapi.dto;

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
public class ContratResponseDTO {

    private Long id;

    private LocalDate dateSignature;

    private BigDecimal montantTotal;

    private boolean valide;
}

