package org.example.hydrocore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioFunctionDTO {

    private Long idFuncionario;
    private String nome;
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAdmissao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    private String eta;
    private String cargo;
    private String descricaoTarefa;
    private String statusTarefa;

}