package org.example.hydrocore.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioGerenteResponseDTO {

    private Integer id;
    private String comentario;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate anoMes;
    private String nomeEtaAdmin;
    private Integer idEtaAdmin;

}
