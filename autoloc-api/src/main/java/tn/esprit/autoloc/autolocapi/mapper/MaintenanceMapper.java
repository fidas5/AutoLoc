
package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.MaintenanceResponseDTO;

@Component
public class MaintenanceMapper {

    public Maintenance toEntity(MaintenanceRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Maintenance maintenance = new Maintenance();

        maintenance.setDateDebut(dto.getDateDebut());
        maintenance.setDateFin(dto.getDateFin());
        maintenance.setDescription(dto.getDescription());

        return maintenance;
    }

    public MaintenanceResponseDTO toResponseDTO(Maintenance maintenance) {

        if (maintenance == null) {
            return null;
        }

        MaintenanceResponseDTO dto = new MaintenanceResponseDTO();

        dto.setId(maintenance.getId());
        dto.setDateDebut(maintenance.getDateDebut());
        dto.setDateFin(maintenance.getDateFin());
        dto.setDescription(maintenance.getDescription());

        if (maintenance.getVehicule() != null) {
            dto.setVehiculeId(maintenance.getVehicule().getId());
        }

        return dto;
    }
}
