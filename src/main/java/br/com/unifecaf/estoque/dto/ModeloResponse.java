package br.com.unifecaf.estoque.dto;

import br.com.unifecaf.estoque.model.Modelo;

public record ModeloResponse(Long id, String nome, Long marcaId, String marca) {

    public static ModeloResponse de(Modelo modelo) {
        return new ModeloResponse(
                modelo.getId(),
                modelo.getNome(),
                modelo.getMarca().getId(),
                modelo.getMarca().getNome());
    }
}
