package org.example.hydrocore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefasSimplesDTO {

    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataConclusao;
    private String prioridade;

}
