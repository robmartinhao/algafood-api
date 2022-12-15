package br.com.algaworks.algafoodapi.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class VendaDiaria {

    private Date data;
    private Long totalVendas;
    private BigDecimal totalFaturado;

    public VendaDiaria(java.sql.Date data, Long totalVendas, BigDecimal totalFaturado) {
        this.data = data;
        this.totalVendas = totalVendas;
        this.totalFaturado = totalFaturado;
    }
}
