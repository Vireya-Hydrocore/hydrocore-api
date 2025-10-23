package org.example.hydrocore.projection;

import java.time.LocalDate;

public interface ComentarioGerenteProjection {

    Integer getId();
    String getComentario();
    LocalDate getAnoMes();
    String getNomeEtaAdmin();
    Integer getIdEtaAdmin();

}
