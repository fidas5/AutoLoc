
package tn.esprit.autoloc.autolocapi.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import tn.esprit.autoloc.autolocapi.domain.StatutReservation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {

    private Long id;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private Long clientId;

    private Long contratId;

    private Long vehiculeId;

    private StatutReservation statut;
}
