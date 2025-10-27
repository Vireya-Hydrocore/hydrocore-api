package org.example.hydrocore.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvisoPatchRequestDTO {

    @NotNull(message = "Campo descrição obrigatório")
    @NotBlank(message = "Campo descrição obrigatório")
    public String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataOcorrencia;

    @NotNull(message = "Campo id eta obrigatório")
    @Positive(message = "O ID do ETA deve ser maior que 0")
    private Integer idEta;

    @NotNull(message = "Campo id prioridade obrigatório")
    @Positive(message = "O ID do prioridade deve ser maior que 0")
    private Integer idPrioridade;

}
