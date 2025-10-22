package org.example.hydrocore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganogramaResponseDTO {

    private Integer id;
    private String nome;
    private String cargo;
    private Integer idSupervisor;
    private String nomeSupervisor;

}
