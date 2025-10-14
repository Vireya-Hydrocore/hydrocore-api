package org.example.hydrocore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioEmailResponseDTO {

    private String nome;
    private String cargo;
    private Integer id;

}
