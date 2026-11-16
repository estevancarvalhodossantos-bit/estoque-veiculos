package br.com.unifecaf.estoque.service;

import br.com.unifecaf.estoque.dto.VeiculoAtualizacao;
import br.com.unifecaf.estoque.dto.VeiculoRequest;
import br.com.unifecaf.estoque.dto.VeiculoResponse;
import br.com.unifecaf.estoque.exception.RecursoNaoEncontradoException;
import br.com.unifecaf.estoque.model.Modelo;
import br.com.unifecaf.estoque.model.StatusVeiculo;
import br.com.unifecaf.estoque.model.TipoVeiculo;
import br.com.unifecaf.estoque.model.Veiculo;
import br.com.unifecaf.estoque.repository.VeiculoRepository;
import br.com.unifecaf.estoque.repository.VeiculoSpecs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;
    private final ModeloService modeloService;

    public VeiculoService(VeiculoRepository repository, ModeloService modeloService) {
        this.repository = repository;
        this.modeloService = modeloService;
    }

    @Transactional
    public VeiculoResponse criar(VeiculoRequest request) {
        Modelo modelo = modeloService.buscarEntidade(request.modeloId());
        Veiculo veiculo = new Veiculo(modelo, request.anoFabricacao(), request.cor().trim(),
                request.preco(), request.quilometragem(), request.tipo(), request.status());
        return VeiculoResponse.de(repository.save(veiculo));
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponse> consultar(String marca, String modelo,
                                           BigDecimal precoMin, BigDecimal precoMax,
                                           Integer anoMin, Integer anoMax,
                                           StatusVeiculo status, TipoVeiculo tipo) {
        return repository
                .findAll(VeiculoSpecs.filtrar(marca, modelo, precoMin, precoMax, anoMin, anoMax, status, tipo))
                .stream()
                .map(VeiculoResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public VeiculoResponse buscar(Long id) {
        return VeiculoResponse.de(buscarEntidade(id));
    }

    /** Atualizacao completa (PUT). */
    @Transactional
    public VeiculoResponse atualizar(Long id, VeiculoRequest request) {
        Veiculo veiculo = buscarEntidade(id);
        veiculo.setModelo(modeloService.buscarEntidade(request.modeloId()));
        veiculo.setAnoFabricacao(request.anoFabricacao());
        veiculo.setCor(request.cor().trim());
        veiculo.setPreco(request.preco());
        veiculo.setQuilometragem(request.quilometragem());
        veiculo.setTipo(request.tipo());
        veiculo.setStatus(request.status());
        return VeiculoResponse.de(repository.save(veiculo));
    }

    /** Atualizacao parcial (PATCH): preco, quilometragem e/ou status. */
    @Transactional
    public VeiculoResponse atualizarParcial(Long id, VeiculoAtualizacao dados) {
        Veiculo veiculo = buscarEntidade(id);
        if (dados.preco() != null) {
            veiculo.setPreco(dados.preco());
        }
        if (dados.quilometragem() != null) {
            veiculo.setQuilometragem(dados.quilometragem());
        }
        if (dados.status() != null) {
            veiculo.setStatus(dados.status());
        }
        return VeiculoResponse.de(repository.save(veiculo));
    }

    @Transactional
    public void remover(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private Veiculo buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veiculo " + id + " nao encontrado"));
    }
}
