package tn.esprit.autoloc.autolocapi.service;


import tn.esprit.autoloc.autolocapi.dto.ContratRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ContratResponseDTO;

import java.util.List;

public interface IContratService {

    ContratResponseDTO create(ContratRequestDTO dto);

    ContratResponseDTO findById(Long id);

    List<ContratResponseDTO> findAll();

    ContratResponseDTO update(Long id, ContratRequestDTO dto);

    void deleteById(Long id);
}
