package org.example.hydrocore.dto.response;

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
public class TarefasResponseDTO {

    private Integer id;
    private String descricao;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacao;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataConclusao;
    private String prioridade;
    private String nome;
    private String status;

}
