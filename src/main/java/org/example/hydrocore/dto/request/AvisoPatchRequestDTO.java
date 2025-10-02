package org.example.hydrocore.dto.request;

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
public class AvisoPatchRequestDTO {

    public String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataOcorrencia;

    private Integer idEta;
    private Integer idPrioridade;

}
