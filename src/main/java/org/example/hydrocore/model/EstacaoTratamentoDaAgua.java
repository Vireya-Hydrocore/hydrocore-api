package org.example.hydrocore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "eta")
@Getter
@Setter
public class EstacaoTratamentoDaAgua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_eta")
    private Integer idEta;

    private String nome;

    private String telefone;

    @Column(name = "capacidade_tratamento")
    private Integer capacidadeTratamento;

    @Column(name = "id_endereco")
    private Integer idEndereco;

}
