
package tn.esprit.autoloc.autolocapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgenceResponseDTO {

    private Long id;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;
}
