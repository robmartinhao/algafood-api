package br.com.algaworks.algafoodapi.infrastructure.service.query;

import br.com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import br.com.algaworks.algafoodapi.domain.model.Pedido;
import br.com.algaworks.algafoodapi.domain.model.StatusPedido;
import br.com.algaworks.algafoodapi.domain.model.dto.VendaDiaria;
import br.com.algaworks.algafoodapi.domain.service.VendaQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var functionConvertTzDataCriacao = builder.function(
                "convert_tz", Date.class, root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(timeOffset));

        var functionDateDataCriacao = builder.function(
                "date", Date.class, functionConvertTzDataCriacao);

        var selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal"))
        );

        var predicates = new ArrayList<Predicate>();
        if (filtro.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
        }
        if (filtro.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }
        if (filtro.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
        }
        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
        query.where(predicates.toArray(new Predicate[0]));

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
