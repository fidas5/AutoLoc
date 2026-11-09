
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.dto.EmployeRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.EmployeResponseDTO;

public interface IEmployeService {

    EmployeResponseDTO create(EmployeRequestDTO dto);

    EmployeResponseDTO findById(Long id);

    List<EmployeResponseDTO> findAll();

    EmployeResponseDTO update(Long id, EmployeRequestDTO dto);

    void deleteById(Long id);
}
