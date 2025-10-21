package org.example.hydrocore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "avisos")
public class Avisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avisos")
    private Integer id;

    private String descricao;

    @Column(name = "data_ocorrencia")
    private LocalDate dataOcorrencia;

    @Column(name = "id_eta")
    private Integer idEta;

    @Column(name = "id_status")
    private Integer idStatus;

    @Column(name = "id_prioridade")
    private Integer idPrioridade;

}

