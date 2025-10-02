package org.example.hydrocore.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "produto")
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Integer idProduto;

    @Column(name = "nome_produto")
    private String nomeProduto;

    private String tipo;

    @Column(name = "unidade_medida")
    private String unidadeMedida;

}
