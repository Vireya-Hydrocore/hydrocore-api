package org.example.hydrocore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumoTarefasEtaResponseDTO {

    private Integer tarefasParaHoje;
    private Integer tarefasFeitas;
    private Integer tarefasNaoRealizadas;
    private Integer tarefasTotais;


}
