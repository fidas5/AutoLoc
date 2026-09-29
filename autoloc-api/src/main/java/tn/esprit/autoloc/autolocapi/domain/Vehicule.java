package tn.esprit.autoloc.autolocapi.domain;

import jakarta.persistence. *;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long idvehicule;

    @Column(nullable = false, unique = true, length = 20)
    private String immatriculation;

    @Column(nullable = false, length = 50)
    private String marque;

    @Column(nullable = false, length = 50)
    private String modele;



    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifJournalier;



    // Vehicule
    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorie;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;

    @ManyToOne
    private Agence agence;

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    private Set<Maintenance> maintenances;

    @OneToMany(mappedBy = "vehicule")
    private Set<Reservation> reservations;

}
