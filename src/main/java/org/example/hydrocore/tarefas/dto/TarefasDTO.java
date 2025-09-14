package org.example.hydrocore.tarefas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefasDTO {

    private Long idTarefa;
    private String descricao;
    private Date dataCriacao;
    private Date dataConclusao;
    private String status;
    private String nivel;
    private Long idFuncionario;

}