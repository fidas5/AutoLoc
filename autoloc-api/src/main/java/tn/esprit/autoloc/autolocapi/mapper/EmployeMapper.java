
package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.dto.EmployeRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.EmployeResponseDTO;

@Component
public class EmployeMapper {

    public Employe toEntity(EmployeRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Employe employe = new Employe();

        employe.setNom(dto.getNom());
        employe.setPrenom(dto.getPrenom());
        employe.setRole(dto.getRole());

        return employe;
    }

    public EmployeResponseDTO toResponseDTO(Employe employe) {

        if (employe == null) {
            return null;
        }

        EmployeResponseDTO dto = new EmployeResponseDTO();

        dto.setId(employe.getId());
        dto.setNom(employe.getNom());
        dto.setPrenom(employe.getPrenom());
        dto.setRole(employe.getRole());

        if (employe.getAgence() != null) {
            dto.setAgenceId(employe.getAgence().getId());
        }

        return dto;
    }
}
