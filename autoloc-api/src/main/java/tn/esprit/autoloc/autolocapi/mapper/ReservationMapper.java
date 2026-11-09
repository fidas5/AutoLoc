
package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.dto.ReservationRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ReservationResponseDTO;

@Component
public class ReservationMapper {

    public Reservation toEntity(ReservationRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setStatut(dto.getStatut());

        return reservation;
    }

    public ReservationResponseDTO toResponseDTO(Reservation reservation) {

        if (reservation == null) {
            return null;
        }

        ReservationResponseDTO dto = new ReservationResponseDTO();

        dto.setId(reservation.getId());
        dto.setDateDebut(reservation.getDateDebut());
        dto.setDateFin(reservation.getDateFin());
        dto.setStatut(reservation.getStatut());

        if (reservation.getClient() != null) {
            dto.setClientId(reservation.getClient().getId());
        }

        if (reservation.getContrat() != null) {
            dto.setContratId(reservation.getContrat().getId());
        }

        if (reservation.getVehicule() != null) {
            dto.setVehiculeId(reservation.getVehicule().getId());
        }

        return dto;
    }
}
