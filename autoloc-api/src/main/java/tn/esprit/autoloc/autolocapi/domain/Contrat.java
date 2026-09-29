package tn.esprit.autoloc.autolocapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;


    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private boolean valide;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL)
    private Set<Paiement> paiements;

    @OneToOne(mappedBy = "contrat")
    private Reservation reservation;
}
