package org.example.hydrocore.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculadoraCoagulacaoRequestDTO {

    @Min(0)
    @Max(500)
    private Integer turbidez;

    @Min(0)
    @Max(14)
    @NotNull(message = "A temperatura não pode ser nula")
    private Double ph;

    @NotNull(message = "A cor não pode estar nula")
    @NotBlank(message = "A cor não pode estar em branco")
    private String cor;

    @Min(0)
    @Max(100)
    @NotNull(message = "O volume não pode estar nulo")
    private Integer volume;

    @Min(0)
    @Max(100)
    @NotNull(message = "A alumina não pode estar nulo")
    private Double alumina;

    @Min(0)
    @Max(100)
    @NotNull(message = "A alcalinidade não pode estar nula")
    private Double alcalinidade;

    @NotNull(message = "O produto químico não pode estar nulo")
    @NotBlank(message = "O produto químico não pode estar em branco")
    private String produtoQuimico;

}
