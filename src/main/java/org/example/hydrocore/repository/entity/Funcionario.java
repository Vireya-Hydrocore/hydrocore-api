package org.example.hydrocore.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "funcionario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private Integer idFuncionario;

    private String nome;

    private String senha;

    @Email
    private String email;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "id_eta")
    private EstacaoTratamentoDaAgua idEta;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo idCargo;

    @OneToMany(mappedBy = "idFuncionario")
    private List<Tarefas> tarefas;

}
