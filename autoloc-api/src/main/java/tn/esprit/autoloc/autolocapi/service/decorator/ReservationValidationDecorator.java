
package tn.esprit.autoloc.autolocapi.service.decorator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.autolocapi.dto.ReservationRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ReservationResponseDTO;
import tn.esprit.autoloc.autolocapi.service.IReservationService;

/**
 * Décorateur de validation des réservations.
 */
@Service
@Primary
public class ReservationValidationDecorator extends ReservationServiceDecorator {

    public ReservationValidationDecorator(
            @Qualifier("reservationServiceCore") IReservationService delegate) {
        super(delegate);
    }

    @Override
    public ReservationResponseDTO create(ReservationRequestDTO dto) {

        verifierDates(dto);

        return super.create(dto);
    }

    @Override
    public ReservationResponseDTO update(
            Long id, ReservationRequestDTO dto) {

        verifierDates(dto);

        return super.update(id, dto);
    }

    private void verifierDates(ReservationRequestDTO dto) {

        if (dto.getDateDebut() == null || dto.getDateFin() == null) {
            throw new IllegalArgumentException(
                    "Les dates de début et de fin sont obligatoires");
        }

        if (dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
