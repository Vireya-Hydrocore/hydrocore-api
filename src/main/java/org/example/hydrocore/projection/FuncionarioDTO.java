package org.example.hydrocore.projection;

import java.time.LocalDate;

public interface FuncionarioDTO {
    Integer getId();
    String getNome();
    String getEmail();
    LocalDate getDataAdmissao();
    LocalDate getDataNascimento();
    String getEta();
    String getCargo();
    String getDescricaoTarefa();
    String getStatusTarefa();
}
