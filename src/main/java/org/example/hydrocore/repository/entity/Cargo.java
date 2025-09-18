package org.example.hydrocore.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cargo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cargo")
    private Long idCargo;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "acesso", columnDefinition = "TEXT") // pode ser VARCHAR ou TEXT, depende do banco
    private String acesso;
}

