
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.dto.AgenceRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.AgenceResponseDTO;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.AgenceMapper;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public AgenceResponseDTO create(AgenceRequestDTO dto) {

        Agence agence = agenceMapper.toEntity(dto);

        Agence saved = agenceRepository.save(agence);

        return agenceMapper.toResponseDTO(saved);
    }

    @Override
    public AgenceResponseDTO findById(Long id) {

        Agence agence = rechercherAgence(id);

        return agenceMapper.toResponseDTO(agence);
    }

    @Override
    public List<AgenceResponseDTO> findAll() {

        return agenceRepository.findAll()
                .stream()
                .map(agenceMapper::toResponseDTO)
                .toList();
    }

    @Override
    public AgenceResponseDTO update(Long id, AgenceRequestDTO dto) {

        Agence existante = rechercherAgence(id);

        existante.setNom(dto.getNom());
        existante.setVille(dto.getVille());
        existante.setAdresse(dto.getAdresse());
        existante.setTelephone(dto.getTelephone());

        Agence updated = agenceRepository.save(existante);

        return agenceMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {

        Agence existante = rechercherAgence(id);

        agenceRepository.delete(existante);
    }

    private Agence rechercherAgence(Long id) {

        return agenceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agence", id));
    }
}
