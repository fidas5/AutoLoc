package tn.esprit.autoloc.autolocapi.service.decorator;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.service.IReservationService;
import tn.esprit.autoloc.autolocapi.service.ReservationServiceImpl;
import tn.esprit.autoloc.autolocapi.web.controller.ReservationController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationValidationDecoratorTests {

    @Test
    void rejectsAnExistingIdBeforeCallingTheDelegate() {
        IReservationService delegate = mock(IReservationService.class);
        Reservation reservation = new Reservation();
        reservation.setId(42L);

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> new ReservationValidationDecorator(delegate).create(reservation));

        assertEquals("Une nouvelle réservation ne doit pas avoir d'identifiant", error.getMessage());
        verifyNoInteractions(delegate);
    }

    @Test
    void delegatesCreationAndReturnsTheSavedReservation() {
        IReservationService delegate = mock(IReservationService.class);
        Reservation input = new Reservation();
        Reservation saved = new Reservation();
        saved.setId(42L);
        when(delegate.create(input)).thenReturn(saved);

        assertSame(saved, new ReservationValidationDecorator(delegate).create(input));
        verify(delegate).create(input);
        verifyNoMoreInteractions(delegate);
    }

    @Test
    void preservesAllOtherOperations() {
        IReservationService delegate = mock(IReservationService.class);
        IReservationService decorated = new ReservationValidationDecorator(delegate);
        Reservation reservation = new Reservation();
        List<Reservation> reservations = List.of(reservation);
        when(delegate.findById(42L)).thenReturn(reservation);
        when(delegate.findAll()).thenReturn(reservations);
        when(delegate.update(42L, reservation)).thenReturn(reservation);

        assertSame(reservation, decorated.findById(42L));
        assertSame(reservations, decorated.findAll());
        assertSame(reservation, decorated.update(42L, reservation));
        decorated.deleteById(42L);

        verify(delegate).findById(42L);
        verify(delegate).findAll();
        verify(delegate).update(42L, reservation);
        verify(delegate).deleteById(42L);
        verifyNoMoreInteractions(delegate);
    }

    @Test
    void propagatesTheOriginalException() {
        IReservationService delegate = mock(IReservationService.class);
        ResourceNotFoundException error = new ResourceNotFoundException("Reservation", 42L);
        when(delegate.findById(42L)).thenThrow(error);

        assertSame(error, assertThrows(ResourceNotFoundException.class,
                () -> new ReservationValidationDecorator(delegate).findById(42L)));
    }

    @Test
    void springInjectsTheDecoratorAndItsQualifiedCoreWithoutADatabase() {
        IReservationRepository repository = mock(IReservationRepository.class);
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(IReservationRepository.class, () -> repository);
            context.register(ReservationServiceImpl.class, ReservationValidationDecorator.class,
                    ReservationController.class);
            context.refresh();

            assertInstanceOf(ReservationValidationDecorator.class,
                    context.getBean(IReservationService.class));
            ReservationController controller = context.getBean(ReservationController.class);
            Reservation invalid = new Reservation();
            invalid.setId(42L);
            assertThrows(IllegalArgumentException.class, () -> controller.create(invalid));
            verifyNoInteractions(repository);

            Reservation valid = new Reservation();
            Reservation saved = new Reservation();
            saved.setId(43L);
            when(repository.save(valid)).thenReturn(saved);
            var response = controller.create(valid);
            assertEquals(201, response.getStatusCode().value());
            assertSame(saved, response.getBody());
            verify(repository).save(valid);
            verifyNoMoreInteractions(repository);
        }
    }
}
