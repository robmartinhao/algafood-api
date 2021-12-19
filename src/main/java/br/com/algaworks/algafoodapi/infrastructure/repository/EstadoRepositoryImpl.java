package br.com.algaworks.algafoodapi.infrastructure.repository;

import br.com.algaworks.algafoodapi.domain.model.Cidade;
import br.com.algaworks.algafoodapi.domain.model.Estado;
import br.com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Estado> listar() {
        return manager.createQuery("from Estado", Estado.class).getResultList();
    }

    @Override
    public Estado buscarPeloId(Long id) {
        return manager.find(Estado.class, id);
    }

    @Override
    @Transactional
    public Estado adicionar(Estado estado) {
        return manager.merge(estado);
    }

    @Override
    @Transactional
    public void remover(Estado estado) {
        estado = buscarPeloId(estado.getId());
        manager.remove(estado);
    }
}
