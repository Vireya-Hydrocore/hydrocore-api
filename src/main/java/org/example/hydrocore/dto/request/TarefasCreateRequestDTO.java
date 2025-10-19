package org.example.hydrocore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefasCreateRequestDTO {


    @NotBlank(message = "A descrição da tarefa é obrigatória.")
    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
    private String descricao;

    @NotBlank(message = "O nível de prioridade é obrigatório.")
    @Size(max = 7, message = "O nível é muito longo.")
    private String prioridade;

}
