package org.example.hydrocore.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoEtaDTO {

    @Column(name = "id_eta")
    private Integer id;
    private String produtos;

}
