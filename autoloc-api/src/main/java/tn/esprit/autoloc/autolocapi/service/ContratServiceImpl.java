package tn.esprit.autoloc.autolocapi.service;

import java.math.BigDecimal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.dto.ContratRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ContratResponseDTO;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.ContratMapper;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final ContratMapper contratMapper;

    @Override
    public ContratResponseDTO create(ContratRequestDTO dto) {

        verifierMontant(dto.getMontantTotal());

        Contrat contrat = contratMapper.toEntity(dto);

        Contrat saved = contratRepository.save(contrat);

        return contratMapper.toResponseDTO(saved);
    }

    @Override
    public ContratResponseDTO findById(Long id) {

        Contrat contrat = rechercherContrat(id);

        return contratMapper.toResponseDTO(contrat);
    }

    @Override
    public List<ContratResponseDTO> findAll() {

        return contratRepository.findAll()
                .stream()
                .map(contratMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ContratResponseDTO update(Long id, ContratRequestDTO dto) {

        Contrat existant = rechercherContrat(id);

        verifierMontant(dto.getMontantTotal());

        existant.setDateSignature(dto.getDateSignature());
        existant.setMontantTotal(dto.getMontantTotal());
        existant.setValide(dto.isValide());

        Contrat updated = contratRepository.save(existant);

        return contratMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {

        Contrat existant = rechercherContrat(id);

        contratRepository.delete(existant);
    }

    private Contrat rechercherContrat(Long id) {

        return contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id));
    }

    private void verifierMontant(BigDecimal montant) {

        if (montant != null
                && montant.compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Le montant total du contrat ne peut pas être négatif");
        }
    }
}
