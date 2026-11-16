package br.com.unifecaf.estoque.controller;

import br.com.unifecaf.estoque.dto.ModeloRequest;
import br.com.unifecaf.estoque.dto.ModeloResponse;
import br.com.unifecaf.estoque.service.ModeloService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    private final ModeloService service;

    public ModeloController(ModeloService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ModeloResponse> criar(@Valid @RequestBody ModeloRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping
    public List<ModeloResponse> listar(@RequestParam(required = false) Long marcaId) {
        return service.listar(marcaId);
    }

    @GetMapping("/{id}")
    public ModeloResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public ModeloResponse atualizar(@PathVariable Long id, @Valid @RequestBody ModeloRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
