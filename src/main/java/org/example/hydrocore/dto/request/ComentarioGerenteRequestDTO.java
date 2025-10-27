package org.example.hydrocore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioGerenteRequestDTO {

    @NotBlank
    @NotNull
    private String comentario;

    @NotNull
    private LocalDate anoMes;

    @NotNull
    @Positive()
    private Integer idEtaAdmin;

}
