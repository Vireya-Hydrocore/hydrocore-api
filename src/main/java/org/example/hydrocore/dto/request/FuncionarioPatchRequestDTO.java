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
public class FuncionarioPatchRequestDTO {

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "O email deve ser válido")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAdmissao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotNull(message = "A estação de tratamento de água (ETA) deve ser informada")
    @Positive(message = "O ID da ETA deve ser maior que 0")
    private Integer idEta;

    @NotNull(message = "O cargo deve ser informado")
    @Positive(message = "O ID do cargo deve ser maior que 0")
    private Integer idCargo;

}

