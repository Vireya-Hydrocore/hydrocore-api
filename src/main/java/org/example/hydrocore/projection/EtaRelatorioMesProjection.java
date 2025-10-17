package org.example.hydrocore.projection;


import java.math.BigInteger;

public interface EtaRelatorioMesProjection {

    Integer getIdEta();
    BigInteger getVolumeTratado();
    String getEtaNome();
    String getEtaAdmin();
    String getCidade();
    String getEstado();
    String getBairro();
    Double getPhMin();
    Double getPhMax();
}

