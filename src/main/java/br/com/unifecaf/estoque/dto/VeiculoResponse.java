package br.com.unifecaf.estoque.dto;

import br.com.unifecaf.estoque.model.StatusVeiculo;
import br.com.unifecaf.estoque.model.TipoVeiculo;
import br.com.unifecaf.estoque.model.Veiculo;

import java.math.BigDecimal;

public record VeiculoResponse(
        Long id,
        Long modeloId,
        String marca,
        String modelo,
        Integer anoFabricacao,
        String cor,
        BigDecimal preco,
        Integer quilometragem,
        TipoVeiculo tipo,
        StatusVeiculo status) {

    public static VeiculoResponse de(Veiculo v) {
        return new VeiculoResponse(
                v.getId(),
                v.getModelo().getId(),
                v.getModelo().getMarca().getNome(),
                v.getModelo().getNome(),
                v.getAnoFabricacao(),
                v.getCor(),
                v.getPreco(),
                v.getQuilometragem(),
                v.getTipo(),
                v.getStatus());
    }
}
