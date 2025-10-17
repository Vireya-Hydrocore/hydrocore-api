package org.example.hydrocore.model;

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
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "acesso", columnDefinition = "TEXT")
    private String acesso;

    @Column(name = "id_pai")
    private Integer idPai;

}

