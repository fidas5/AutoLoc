
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.MaintenanceResponseDTO;

public interface IMaintenanceService {

    MaintenanceResponseDTO create(MaintenanceRequestDTO dto);

    MaintenanceResponseDTO findById(Long id);

    List<MaintenanceResponseDTO> findAll();

    MaintenanceResponseDTO update(Long id, MaintenanceRequestDTO dto);

    void deleteById(Long id);
}
