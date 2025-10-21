package org.example.hydrocore.projection;

import java.time.LocalDate;

public interface AvisosProjection {

    Integer getId();
    String getDescricao();
    LocalDate getDataOcorrencia();
    String getNomeEta();
    String getPrioridade();
    String getStatus();

}
