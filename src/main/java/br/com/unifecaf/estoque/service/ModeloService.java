package br.com.unifecaf.estoque.service;

import br.com.unifecaf.estoque.dto.ModeloRequest;
import br.com.unifecaf.estoque.dto.ModeloResponse;
import br.com.unifecaf.estoque.exception.RecursoNaoEncontradoException;
import br.com.unifecaf.estoque.model.Marca;
import br.com.unifecaf.estoque.model.Modelo;
import br.com.unifecaf.estoque.repository.ModeloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModeloService {

    private final ModeloRepository repository;
    private final MarcaService marcaService;

    public ModeloService(ModeloRepository repository, MarcaService marcaService) {
        this.repository = repository;
        this.marcaService = marcaService;
    }

    @Transactional
    public ModeloResponse criar(ModeloRequest request) {
        Marca marca = marcaService.buscarEntidade(request.marcaId());
        Modelo modelo = repository.save(new Modelo(request.nome().trim(), marca));
        return ModeloResponse.de(modelo);
    }

    @Transactional(readOnly = true)
    public List<ModeloResponse> listar(Long marcaId) {
        List<Modelo> modelos = (marcaId == null) ? repository.findAll() : repository.findByMarcaId(marcaId);
        return modelos.stream().map(ModeloResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public ModeloResponse buscar(Long id) {
        return ModeloResponse.de(buscarEntidade(id));
    }

    @Transactional
    public ModeloResponse atualizar(Long id, ModeloRequest request) {
        Modelo modelo = buscarEntidade(id);
        modelo.setNome(request.nome().trim());
        modelo.setMarca(marcaService.buscarEntidade(request.marcaId()));
        return ModeloResponse.de(repository.save(modelo));
    }

    @Transactional
    public void remover(Long id) {
        repository.delete(buscarEntidade(id));
    }

    public Modelo buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo " + id + " nao encontrado"));
    }
}
