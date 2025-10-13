package org.example.hydrocore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDosagemResponseDTO {
    private String produto;
    private double quantidade;
}
