package org.example.hydrocore.repository;

import org.example.hydrocore.dto.EtaDTO;
import org.example.hydrocore.projection.EtaDataRelatorioProjection;
import org.example.hydrocore.projection.EtaRelatorioMesProjection;
import org.example.hydrocore.model.EstacaoTratamentoDaAgua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryEta extends JpaRepository<EstacaoTratamentoDaAgua, Integer> {

    @Query(value = "SELECT e.id_eta AS id, e.nome, e.telefone, e.capacidade_tratamento AS capacidadeTratamento, en.cidade FROM eta e JOIN endereco en ON en.id_endereco = e.id_endereco", nativeQuery = true)
    List<EtaDTO> mostrarEtas();

    @Query(value = "SELECT * FROM gerar_relatorio_mes(:mes, :ano)", nativeQuery = true)
    List<EtaRelatorioMesProjection> gerarRelatorioMes(@Param("mes") Integer mes, @Param("ano") Integer ano);

    @Query(value = "SELECT * FROM data_relatorio_eta(:idEta)", nativeQuery = true)
    List<EtaDataRelatorioProjection> listarDataRelatorio(@Param("idEta") Integer idEta);

}
