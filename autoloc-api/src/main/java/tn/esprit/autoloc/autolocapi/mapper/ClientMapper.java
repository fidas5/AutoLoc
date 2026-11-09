
package tn.esprit.autoloc.autolocapi.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.dto.ClientRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ClientResponseDTO;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Client client = new Client();

        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        client.setNumPermis(dto.getNumPermis());
        client.setDateInscription(dto.getDateInscription());

        return client;
    }

    public ClientResponseDTO toResponseDTO(Client client) {

        if (client == null) {
            return null;
        }

        ClientResponseDTO dto = new ClientResponseDTO();

        dto.setId(client.getId());
        dto.setNom(client.getNom());
        dto.setPrenom(client.getPrenom());
        dto.setEmail(client.getEmail());
        dto.setTelephone(client.getTelephone());
        dto.setNumPermis(client.getNumPermis());
        dto.setDateInscription(client.getDateInscription());

        return dto;
    }
}
