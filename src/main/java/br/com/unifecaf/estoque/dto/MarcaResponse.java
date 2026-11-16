package br.com.unifecaf.estoque.dto;

import br.com.unifecaf.estoque.model.Marca;

public record MarcaResponse(Long id, String nome) {

    public static MarcaResponse de(Marca marca) {
        return new MarcaResponse(marca.getId(), marca.getNome());
    }
}
