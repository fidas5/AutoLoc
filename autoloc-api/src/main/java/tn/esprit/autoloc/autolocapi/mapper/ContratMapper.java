package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.dto.ContratRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ContratResponseDTO;

@Component
public class ContratMapper {

    // Convertir RequestDTO vers Entity
    public Contrat toEntity(ContratRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Contrat contrat = new Contrat();

        contrat.setDateSignature(dto.getDateSignature());
        contrat.setMontantTotal(dto.getMontantTotal());
        contrat.setValide(dto.isValide());

        return contrat;
    }

    // Convertir Entity vers ResponseDTO
    public ContratResponseDTO toResponseDTO(Contrat contrat) {
        if (contrat == null) {
            return null;
        }

        ContratResponseDTO dto = new ContratResponseDTO();

        dto.setId(contrat.getId());
        dto.setDateSignature(contrat.getDateSignature());
        dto.setMontantTotal(contrat.getMontantTotal());
        dto.setValide(contrat.isValide());

        return dto;
    }
}
