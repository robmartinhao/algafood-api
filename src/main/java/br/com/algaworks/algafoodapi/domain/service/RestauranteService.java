package br.com.algaworks.algafoodapi.domain.service;

import br.com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafoodapi.domain.exception.RestauranteNaoEncontradoException;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestauranteService {

    public static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso";
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CidadeService cidadeService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_RESTAURANTE_EM_USO, id));
        }
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restauranteEncontrado = buscarOuFalhar(id);
        restauranteEncontrado.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restauranteEncontrado = buscarOuFalhar(id);
        restauranteEncontrado.inativar();
    }

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }
}
