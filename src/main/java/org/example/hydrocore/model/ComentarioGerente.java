package org.example.hydrocore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comentario_gerente")
public class ComentarioGerente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Integer id;

    private String comentario;

    @Column(name = "ano_mes")
    private LocalDate anoMes;

    @Column(name = "id_eta_admin")
    private Integer idEtaAdmin;

}
