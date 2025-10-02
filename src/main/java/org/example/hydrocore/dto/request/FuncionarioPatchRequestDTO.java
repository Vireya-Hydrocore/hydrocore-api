package org.example.hydrocore.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioPatchRequestDTO {

    private String nome;
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAdmissao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
    private Integer idEta;
    private Integer idCargo;

}

