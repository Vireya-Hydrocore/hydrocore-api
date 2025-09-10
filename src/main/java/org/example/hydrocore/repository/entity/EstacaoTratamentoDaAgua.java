package org.example.hydrocore.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "eta")
@Getter
@Setter
public class EstacaoTratamentoDaAgua {

    @Id
    @Column(name = "id_eta")
    private Long idEta;

    private String nome;

    private String telefone;

    @Column(name = "capacidade_tratamento")
    private Integer capacidadeTratamento;

    @Column(name = "id_endereco")
    private Long idEndereco;

}
