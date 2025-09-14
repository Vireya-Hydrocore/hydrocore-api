package org.example.hydrocore.tarefas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefasResponseDTO {

    private Long idTarefa;
    private String descricao;
    private Date dataCriacao;
    private Date dataConclusao;
    private String status;
    private Long idFuncionario;
    private String nivel;

}
