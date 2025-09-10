package org.example.hydrocore.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "produto")
@Getter
@Setter
public class Produto {

    @Id
    private Long idProduto;

    @Column(name = "nome_produto")
    private String nomeProduto;

    private String tipo;

    @Column(name = "unidade_medida")
    private String unidadeMedida;

}
