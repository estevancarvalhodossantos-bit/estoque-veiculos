package br.com.unifecaf.estoque.service;

import br.com.unifecaf.estoque.dto.MarcaRequest;
import br.com.unifecaf.estoque.dto.MarcaResponse;
import br.com.unifecaf.estoque.exception.RecursoNaoEncontradoException;
import br.com.unifecaf.estoque.exception.RegraDeNegocioException;
import br.com.unifecaf.estoque.model.Marca;
import br.com.unifecaf.estoque.repository.MarcaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarcaService {

    private final MarcaRepository repository;

    public MarcaService(MarcaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MarcaResponse criar(MarcaRequest request) {
        if (repository.existsByNomeIgnoreCase(request.nome().trim())) {
            throw new RegraDeNegocioException("Ja existe uma marca com o nome '" + request.nome() + "'");
        }
        Marca marca = repository.save(new Marca(request.nome().trim()));
        return MarcaResponse.de(marca);
    }

    @Transactional(readOnly = true)
    public List<MarcaResponse> listar() {
        return repository.findAll().stream().map(MarcaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public MarcaResponse buscar(Long id) {
        return MarcaResponse.de(buscarEntidade(id));
    }

    @Transactional
    public MarcaResponse atualizar(Long id, MarcaRequest request) {
        Marca marca = buscarEntidade(id);
        marca.setNome(request.nome().trim());
        return MarcaResponse.de(repository.save(marca));
    }

    @Transactional
    public void remover(Long id) {
        repository.delete(buscarEntidade(id));
    }

    public Marca buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca " + id + " nao encontrada"));
    }
}
