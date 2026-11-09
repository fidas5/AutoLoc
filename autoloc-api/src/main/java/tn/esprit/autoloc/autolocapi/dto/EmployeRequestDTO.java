
package tn.esprit.autoloc.autolocapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import tn.esprit.autoloc.autolocapi.domain.RoleEmploye;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeRequestDTO {

    @NotBlank(message = "Le nom de l'employé est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom de l'employé est obligatoire")
    private String prenom;

    @NotNull(message = "Le rôle de l'employé est obligatoire")
    private RoleEmploye role;

    private Long agenceId;
}
