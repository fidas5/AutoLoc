
package tn.esprit.autoloc.autolocapi.service;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import tn.esprit.autoloc.autolocapi.dto.ReservationRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ReservationResponseDTO;

import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.ReservationMapper;

import tn.esprit.autoloc.autolocapi.repository.IClientRepository;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Service("reservationServiceCore")
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IContratRepository contratRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationResponseDTO create(ReservationRequestDTO dto) {

        verifierDates(dto.getDateDebut(), dto.getDateFin());

        Reservation reservation = reservationMapper.toEntity(dto);

        reservation.setClient(rechercherClient(dto.getClientId()));
        reservation.setContrat(rechercherContrat(dto.getContratId()));
        reservation.setVehicule(rechercherVehicule(dto.getVehiculeId()));

        Reservation saved = reservationRepository.save(reservation);

        return reservationMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO findById(Long id) {

        Reservation reservation = rechercherReservation(id);

        return reservationMapper.toResponseDTO(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> findAll() {

        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public ReservationResponseDTO update(
            Long id, ReservationRequestDTO dto) {

        Reservation existante = rechercherReservation(id);

        verifierDates(dto.getDateDebut(), dto.getDateFin());

        existante.setDateDebut(dto.getDateDebut());
        existante.setDateFin(dto.getDateFin());
        existante.setStatut(dto.getStatut());

        existante.setClient(rechercherClient(dto.getClientId()));
        existante.setContrat(rechercherContrat(dto.getContratId()));
        existante.setVehicule(rechercherVehicule(dto.getVehiculeId()));

        Reservation updated = reservationRepository.save(existante);

        return reservationMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Reservation existante = rechercherReservation(id);

        reservationRepository.delete(existante);
    }

    private Reservation rechercherReservation(Long id) {

        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));
    }

    private Client rechercherClient(Long id) {

        if (id == null) {
            return null;
        }

        return clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", id));
    }

    private Contrat rechercherContrat(Long id) {

        if (id == null) {
            return null;
        }

        return contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id));
    }

    private Vehicule rechercherVehicule(Long id) {

        if (id == null) {
            return null;
        }

        return vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id));
    }

    private void verifierDates(LocalDate debut, LocalDate fin) {

        if (debut == null || fin == null) {
            throw new IllegalArgumentException(
                    "Les dates de début et de fin sont obligatoires");
        }

        if (fin.isBefore(debut)) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
