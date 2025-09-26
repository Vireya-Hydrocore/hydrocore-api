package org.example.hydrocore.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvisosResponseDTO {

    private Long idAvisos;

    private String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataOcorrencia;

    private Long idEta;

    private Long idPrioridade;

}
