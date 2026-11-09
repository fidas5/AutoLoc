
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.dto.PaiementResponseDTO;

public interface IPaiementService {

    PaiementResponseDTO findById(Long id);

    List<PaiementResponseDTO> findAll();
}
