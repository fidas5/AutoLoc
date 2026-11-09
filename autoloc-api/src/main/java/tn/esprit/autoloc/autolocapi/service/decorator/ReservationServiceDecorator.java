
package tn.esprit.autoloc.autolocapi.service.decorator;

import java.util.List;

import tn.esprit.autoloc.autolocapi.dto.ReservationRequestDTO;
import tn.esprit.autoloc.autolocapi.dto.ReservationResponseDTO;
import tn.esprit.autoloc.autolocapi.service.IReservationService;

/**
 * Base du patron Décorateur :
 * conserve le contrat et délègue les opérations.
 */
public abstract class ReservationServiceDecorator implements IReservationService {

    protected final IReservationService delegate;

    protected ReservationServiceDecorator(IReservationService delegate) {
        this.delegate = delegate;
    }

    @Override
    public ReservationResponseDTO create(ReservationRequestDTO dto) {
        return delegate.create(dto);
    }

    @Override
    public ReservationResponseDTO findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public List<ReservationResponseDTO> findAll() {
        return delegate.findAll();
    }

    @Override
    public ReservationResponseDTO update(
            Long id, ReservationRequestDTO dto) {

        return delegate.update(id, dto);
    }

    @Override
    public void deleteById(Long id) {
        delegate.deleteById(id);
    }
}
