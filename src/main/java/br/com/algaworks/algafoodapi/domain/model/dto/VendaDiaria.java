package br.com.algaworks.algafoodapi.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class VendaDiaria {

    private Date data;
    private Long totalVendas;
    private BigDecimal totalFaturado;
}
