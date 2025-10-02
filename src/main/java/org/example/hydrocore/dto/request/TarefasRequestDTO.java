package org.example.hydrocore.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefasRequestDTO {

    @NotBlank(message = "A descrição da tarefa é obrigatória.")
    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
    private String descricao;

    @NotBlank(message = "O status da tarefa é obrigatório.")
    @Size(max = 50, message = "O status é muito longo.")
    private String status;

    @NotBlank(message = "O nível de prioridade é obrigatório.")
    @Size(max = 50, message = "O nível é muito longo.")
    private String nivel;

}
