package br.com.algaworks.algafoodapi.infrastructure.service;

import br.com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import br.com.algaworks.algafoodapi.domain.model.dto.VendaDiaria;
import br.com.algaworks.algafoodapi.domain.service.VendaQueryService;

import java.util.List;

public class VendaQueryServiceImpl implements VendaQueryService {
    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        return null;
    }
}
