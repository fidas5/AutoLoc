
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.dto.EmployeRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.EmployeResponseDTO;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.EmployeMapper;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;
import tn.esprit.autoloc.autolocapi.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    public EmployeResponseDTO create(EmployeRequestDTO dto) {

        Employe employe = employeMapper.toEntity(dto);

        employe.setAgence(rechercherAgence(dto.getAgenceId()));

        Employe saved = employeRepository.save(employe);

        return employeMapper.toResponseDTO(saved);
    }

    @Override
    public EmployeResponseDTO findById(Long id) {

        Employe employe = rechercherEmploye(id);

        return employeMapper.toResponseDTO(employe);
    }

    @Override
    public List<EmployeResponseDTO> findAll() {

        return employeRepository.findAll()
                .stream()
                .map(employeMapper::toResponseDTO)
                .toList();
    }

    @Override
    public EmployeResponseDTO update(Long id, EmployeRequestDTO dto) {

        Employe existant = rechercherEmploye(id);

        existant.setNom(dto.getNom());
        existant.setPrenom(dto.getPrenom());
        existant.setRole(dto.getRole());
        existant.setAgence(rechercherAgence(dto.getAgenceId()));

        Employe updated = employeRepository.save(existant);

        return employeMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {

        Employe existant = rechercherEmploye(id);

        employeRepository.delete(existant);
    }

    private Employe rechercherEmploye(Long id) {

        return employeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employe", id));
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
