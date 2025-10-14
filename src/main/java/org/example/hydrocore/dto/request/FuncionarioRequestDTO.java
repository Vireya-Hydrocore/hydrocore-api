package org.example.hydrocore.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioRequestDTO {

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "A senha não pode ser vazia")
    @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
    private String senha;

    @NotNull(message = "A data de admissão é obrigatória")
    @PastOrPresent(message = "A data de admissão não pode ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAdmissao;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve estar no passado")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotNull(message = "A estação de tratamento de água (ETA) deve ser informada")
    @Positive(message = "O ID da ETA deve ser maior que 0")
    private Integer idEta;

    @NotNull(message = "O cargo deve ser informado")
    @Positive(message = "O ID do cargo deve ser maior que 0")
    private Integer idCargo;
}

