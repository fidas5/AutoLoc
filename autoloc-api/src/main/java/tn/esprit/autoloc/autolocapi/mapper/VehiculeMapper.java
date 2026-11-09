
package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.VehiculeResponseDTO;

@Component
public class VehiculeMapper {

    public Vehicule toEntity(VehiculeRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Vehicule vehicule = new Vehicule();

        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setTarifJournalier(dto.getTarifJournalier());
        vehicule.setCategorie(dto.getCategorie());
        vehicule.setStatut(dto.getStatut());

        return vehicule;
    }

    public VehiculeResponseDTO toResponseDTO(Vehicule vehicule) {

        if (vehicule == null) {
            return null;
        }

        VehiculeResponseDTO dto = new VehiculeResponseDTO();

        dto.setId(vehicule.getId());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setMarque(vehicule.getMarque());
        dto.setModele(vehicule.getModele());
        dto.setTarifJournalier(vehicule.getTarifJournalier());
        dto.setCategorie(vehicule.getCategorie());
        dto.setStatut(vehicule.getStatut());

        if (vehicule.getAgence() != null) {
            dto.setAgenceId(vehicule.getAgence().getId());
        }

        return dto;
    }
}
