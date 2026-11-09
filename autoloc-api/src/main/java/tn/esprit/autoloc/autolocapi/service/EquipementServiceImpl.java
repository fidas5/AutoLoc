
package tn.esprit.autoloc.autolocapi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.dto.EquipementRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.EquipementResponseDTO;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.EquipementMapper;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final EquipementMapper equipementMapper;

    @Override
    @Transactional
    public EquipementResponseDTO create(EquipementRequestDTO dto) {

        Equipement equipement = equipementMapper.toEntity(dto);

        equipement.setVehicules(rechercherVehicules(dto.getVehiculeIds()));

        Equipement saved = equipementRepository.save(equipement);

        return equipementMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EquipementResponseDTO findById(Long id) {

        Equipement equipement = rechercherEquipement(id);

        return equipementMapper.toResponseDTO(equipement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipementResponseDTO> findAll() {

        return equipementRepository.findAll()
                .stream()
                .map(equipementMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public EquipementResponseDTO update(Long id, EquipementRequestDTO dto) {

        Equipement existant = rechercherEquipement(id);

        existant.setLibelle(dto.getLibelle());
        existant.setVehicules(rechercherVehicules(dto.getVehiculeIds()));

        Equipement updated = equipementRepository.save(existant);

        return equipementMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Equipement existant = rechercherEquipement(id);

        equipementRepository.delete(existant);
    }

    private Equipement rechercherEquipement(Long id) {

        return equipementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipement", id));
    }

    private Set<Vehicule> rechercherVehicules(Set<Long> ids) {

        Set<Vehicule> vehicules = new HashSet<>();

        if (ids == null) {
            return vehicules;
        }

        for (Long id : ids) {
            Vehicule vehicule = vehiculeRepository.findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Vehicule", id));

            vehicules.add(vehicule);
        }

        return vehicules;
    }
}
