package tn.esprit.autoloc.autolocapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long id;

    private LocalDate dateDebut;

    private LocalDate dateFin;


    @ManyToOne
    private Client client;

    @OneToOne
    private Contrat contrat;

    @ManyToOne
    private Vehicule vehicule;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

}
