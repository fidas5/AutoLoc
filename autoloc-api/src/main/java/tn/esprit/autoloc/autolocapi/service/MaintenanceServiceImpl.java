
package tn.esprit.autoloc.autolocapi.service;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import tn.esprit.autoloc.autolocapi.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.MaintenanceResponseDTO;

import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.MaintenanceMapper;

import tn.esprit.autoloc.autolocapi.repository.IMaintenanceRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    @Transactional
    public MaintenanceResponseDTO create(MaintenanceRequestDTO dto) {

        verifierDates(dto.getDateDebut(), dto.getDateFin());

        Maintenance maintenance = maintenanceMapper.toEntity(dto);

        maintenance.setVehicule(rechercherVehicule(dto.getVehiculeId()));

        Maintenance saved = maintenanceRepository.save(maintenance);

        return maintenanceMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceResponseDTO findById(Long id) {

        Maintenance maintenance = rechercherMaintenance(id);

        return maintenanceMapper.toResponseDTO(maintenance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> findAll() {

        return maintenanceRepository.findAll()
                .stream()
                .map(maintenanceMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public MaintenanceResponseDTO update(Long id, MaintenanceRequestDTO dto) {

        Maintenance existante = rechercherMaintenance(id);

        verifierDates(dto.getDateDebut(), dto.getDateFin());

        existante.setDateDebut(dto.getDateDebut());
        existante.setDateFin(dto.getDateFin());
        existante.setDescription(dto.getDescription());

        existante.setVehicule(rechercherVehicule(dto.getVehiculeId()));

        Maintenance updated = maintenanceRepository.save(existante);

        return maintenanceMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Maintenance existante = rechercherMaintenance(id);

        maintenanceRepository.delete(existante);
    }

    private Maintenance rechercherMaintenance(Long id) {

        return maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance", id));
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
