package br.com.unifecaf.estoque.dto;

import br.com.unifecaf.estoque.model.StatusVeiculo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

/** Atualizacao parcial: somente os campos enviados (nao nulos) sao alterados. */
public record VeiculoAtualizacao(
        @DecimalMin(value = "0.01", message = "O preco deve ser maior que zero")
        BigDecimal preco,

        @Min(value = 0, message = "A quilometragem nao pode ser negativa")
        Integer quilometragem,

        StatusVeiculo status) {
}
