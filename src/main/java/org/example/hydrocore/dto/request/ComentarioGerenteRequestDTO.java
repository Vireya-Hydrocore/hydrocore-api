package org.example.hydrocore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private Integer idEtaAdmin;

}
