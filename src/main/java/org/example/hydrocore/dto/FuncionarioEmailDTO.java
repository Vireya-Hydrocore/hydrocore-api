package org.example.hydrocore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioEmailDTO {

    private String nome;
    private String cargo;
    private Integer id;

}
