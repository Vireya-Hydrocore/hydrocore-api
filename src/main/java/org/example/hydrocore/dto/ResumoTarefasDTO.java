package org.example.hydrocore.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumoTarefasDTO {

    @Column(name = "tarefas_para_hoje")
    private Integer tarefasParaHoje;

    @Column(name = "tarefas_feitas")
    private Integer tarefasFeitas;

    @Column(name = "tarefas_nao_realizadas")
    private Integer tarefasNaoRealizadas;

    @Column(name = "tarefas_totais")
    private Integer tarefasTotais;

}
