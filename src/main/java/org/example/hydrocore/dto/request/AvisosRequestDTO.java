package org.example.hydrocore.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvisosRequestDTO {

    @NonNull
    @NotBlank
    private String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NonNull
    private LocalDate dataOcorrencia;

    @NonNull
    @Min(1)
    private Integer idEta;

    @NonNull
    @Min(1)
    private Integer idPrioridade;

}
