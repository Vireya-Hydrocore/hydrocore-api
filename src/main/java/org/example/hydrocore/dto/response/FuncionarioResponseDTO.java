package org.example.hydrocore.dto.response;

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
public class FuncionarioResponseDTO {

    private Integer id;
    private String nome;
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAdmissao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    private String eta;
    private String cargo;

}