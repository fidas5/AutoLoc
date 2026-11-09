
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.dto.ClientRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ClientResponseDTO;

public interface IClientService {

    ClientResponseDTO create(ClientRequestDTO dto);

    ClientResponseDTO findById(Long id);

    List<ClientResponseDTO> findAll();

    ClientResponseDTO update(Long id, ClientRequestDTO dto);

    void deleteById(Long id);
}
