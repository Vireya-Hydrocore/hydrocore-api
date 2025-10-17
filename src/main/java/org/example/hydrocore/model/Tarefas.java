package org.example.hydrocore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "tarefa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarefas {

    @Id
    @Column(name = "id_tarefa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTarefa;

    private String descricao;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    private Integer idStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_prioridade", nullable = false)
    private Prioridade prioridade;

    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario idFuncionario;

}
