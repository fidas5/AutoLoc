
package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.dto.PaiementResponseDTO;

@Component
public class PaiementMapper {

    public PaiementResponseDTO toResponseDTO(Paiement paiement) {

        if (paiement == null) {
            return null;
        }

        PaiementResponseDTO dto = new PaiementResponseDTO();

        dto.setId(paiement.getId());
        dto.setMontant(paiement.getMontant());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setModePaiement(paiement.getModePaiement());

        if (paiement.getContrat() != null) {
            dto.setContratId(paiement.getContrat().getId());
        }

        return dto;
    }
}
