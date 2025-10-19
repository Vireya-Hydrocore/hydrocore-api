package org.example.hydrocore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public interface TarefasProjection {

    Integer getId();
    String getDescricao();
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate getDataCriacao();
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate getDataConclusao();
    String getPrioridade();
    String getNome();
    String getStatus();

}