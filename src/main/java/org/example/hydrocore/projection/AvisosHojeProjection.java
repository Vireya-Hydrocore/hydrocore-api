package org.example.hydrocore.projection;

import java.time.LocalDate;

public interface AvisosHojeProjection {

    Integer getId();
    String getNome();
    String getDescricao();
    LocalDate getDataCriacao();
    String getStatus();

}
