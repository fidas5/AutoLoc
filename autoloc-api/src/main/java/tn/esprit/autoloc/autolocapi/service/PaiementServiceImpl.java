
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.dto.PaiementResponseDTO;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.PaiementMapper;
import tn.esprit.autoloc.autolocapi.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;

    @Override
    public PaiementResponseDTO findById(Long id) {

        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paiement", id));

        return paiementMapper.toResponseDTO(paiement);
    }

    @Override
    public List<PaiementResponseDTO> findAll() {

        return paiementRepository.findAll()
                .stream()
                .map(paiementMapper::toResponseDTO)
                .toList();
    }
}
