package br.com.unifecaf.estoque.controller;

import br.com.unifecaf.estoque.dto.VeiculoAtualizacao;
import br.com.unifecaf.estoque.dto.VeiculoRequest;
import br.com.unifecaf.estoque.dto.VeiculoResponse;
import br.com.unifecaf.estoque.model.StatusVeiculo;
import br.com.unifecaf.estoque.model.TipoVeiculo;
import br.com.unifecaf.estoque.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<VeiculoResponse> criar(@Valid @RequestBody VeiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    // READ (lista com filtros opcionais)
    @GetMapping
    public List<VeiculoResponse> consultar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) Integer anoMin,
            @RequestParam(required = false) Integer anoMax,
            @RequestParam(required = false) StatusVeiculo status,
            @RequestParam(required = false) TipoVeiculo tipo) {
        return service.consultar(marca, modelo, precoMin, precoMax, anoMin, anoMax, status, tipo);
    }

    // READ (por id)
    @GetMapping("/{id}")
    public VeiculoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // UPDATE (completo)
    @PutMapping("/{id}")
    public VeiculoResponse atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoRequest request) {
        return service.atualizar(id, request);
    }

    // UPDATE (parcial: preco, quilometragem, status)
    @PatchMapping("/{id}")
    public VeiculoResponse atualizarParcial(@PathVariable Long id,
                                            @Valid @RequestBody VeiculoAtualizacao dados) {
        return service.atualizarParcial(id, dados);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
