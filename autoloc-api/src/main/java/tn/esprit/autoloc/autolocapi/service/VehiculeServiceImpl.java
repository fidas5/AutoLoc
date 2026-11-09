
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.VehiculeResponseDTO;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.VehiculeMapper;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeResponseDTO create(VehiculeRequestDTO dto) {

        Vehicule vehicule = vehiculeMapper.toEntity(dto);

        vehicule.setAgence(rechercherAgence(dto.getAgenceId()));

        Vehicule saved = vehiculeRepository.save(vehicule);

        return vehiculeMapper.toResponseDTO(saved);
    }

    @Override
    public VehiculeResponseDTO findById(Long id) {

        Vehicule vehicule = rechercherVehicule(id);

        return vehiculeMapper.toResponseDTO(vehicule);
    }

    @Override
    public List<VehiculeResponseDTO> findAll() {

        return vehiculeRepository.findAll()
                .stream()
                .map(vehiculeMapper::toResponseDTO)
                .toList();
    }

    @Override
    public VehiculeResponseDTO update(Long id, VehiculeRequestDTO dto) {

        Vehicule existant = rechercherVehicule(id);

        existant.setImmatriculation(dto.getImmatriculation());
        existant.setMarque(dto.getMarque());
        existant.setModele(dto.getModele());
        existant.setTarifJournalier(dto.getTarifJournalier());
        existant.setCategorie(dto.getCategorie());
        existant.setStatut(dto.getStatut());
        existant.setAgence(rechercherAgence(dto.getAgenceId()));

        Vehicule updated = vehiculeRepository.save(existant);

        return vehiculeMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {

        Vehicule existant = rechercherVehicule(id);

        vehiculeRepository.delete(existant);
    }

    private Vehicule rechercherVehicule(Long id) {

        return vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id));
    }

    private Agence rechercherAgence(Long agenceId) {

        if (agenceId == null) {
            return null;
        }

        return agenceRepository.findById(agenceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agence", agenceId));
    }
}
