package org.example.hydrocore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoEtaResponseDTO {

    private Integer id;
    private List<String> produtos;

}
