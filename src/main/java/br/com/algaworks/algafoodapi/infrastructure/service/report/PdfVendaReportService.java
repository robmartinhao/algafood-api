package br.com.algaworks.algafoodapi.infrastructure.service.report;

import br.com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import br.com.algaworks.algafoodapi.domain.service.VendaReportService;
import org.springframework.stereotype.Service;

@Service
public class PdfVendaReportService implements VendaReportService {
    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        return new byte[0];
    }
}
