
package tn.esprit.autoloc.autolocapi.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.dto.EquipementRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.EquipementResponseDTO;

@Component
public class EquipementMapper {

    public Equipement toEntity(EquipementRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Equipement equipement = new Equipement();

        equipement.setLibelle(dto.getLibelle());

        return equipement;
    }

    public EquipementResponseDTO toResponseDTO(Equipement equipement) {

        if (equipement == null) {
            return null;
        }

        EquipementResponseDTO dto = new EquipementResponseDTO();

        dto.setId(equipement.getId());
        dto.setLibelle(equipement.getLibelle());

        if (equipement.getVehicules() != null) {
            dto.setVehiculeIds(
                    equipement.getVehicules()
                            .stream()
                            .map(v -> v.getId())
                            .collect(Collectors.toSet())
            );
        }

        return dto;
    }
}
