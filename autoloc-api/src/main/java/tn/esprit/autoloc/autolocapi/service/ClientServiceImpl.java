
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.dto.ClientRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ClientResponseDTO;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.ClientMapper;
import tn.esprit.autoloc.autolocapi.repository.IClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponseDTO create(ClientRequestDTO dto) {

        Client client = clientMapper.toEntity(dto);

        Client saved = clientRepository.save(client);

        return clientMapper.toResponseDTO(saved);
    }

    @Override
    public ClientResponseDTO findById(Long id) {

        Client client = rechercherClient(id);

        return clientMapper.toResponseDTO(client);
    }

    @Override
    public List<ClientResponseDTO> findAll() {

        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {

        Client existant = rechercherClient(id);

        existant.setNom(dto.getNom());
        existant.setPrenom(dto.getPrenom());
        existant.setEmail(dto.getEmail());
        existant.setTelephone(dto.getTelephone());
        existant.setNumPermis(dto.getNumPermis());
        existant.setDateInscription(dto.getDateInscription());

        Client updated = clientRepository.save(existant);

        return clientMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {

        Client existant = rechercherClient(id);

        clientRepository.delete(existant);
    }

    private Client rechercherClient(Long id) {

        return clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", id));
    }
}
