
package tn.esprit.autoloc.autolocapi.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDTO {

    @NotBlank(message = "Le nom du client est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom du client est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email du client est obligatoire")
    @Email(message = "Le format de l'email est invalide")
    private String email;

    @NotBlank(message = "Le téléphone du client est obligatoire")
    private String telephone;

    @NotBlank(message = "Le numéro de permis est obligatoire")
    private String numPermis;

    private LocalDate dateInscription;
}
