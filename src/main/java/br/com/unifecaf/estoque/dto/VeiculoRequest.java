package br.com.unifecaf.estoque.dto;

import br.com.unifecaf.estoque.model.StatusVeiculo;
import br.com.unifecaf.estoque.model.TipoVeiculo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record VeiculoRequest(
        @NotNull(message = "O modelo e obrigatorio")
        Long modeloId,

        @NotNull(message = "O ano de fabricacao e obrigatorio")
        @Min(value = 1950, message = "O ano de fabricacao deve ser maior ou igual a 1950")
        @Max(value = 2100, message = "O ano de fabricacao deve ser menor ou igual a 2100")
        Integer anoFabricacao,

        @NotBlank(message = "A cor e obrigatoria")
        String cor,

        @NotNull(message = "O preco e obrigatorio")
        @DecimalMin(value = "0.01", message = "O preco deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "A quilometragem e obrigatoria")
        @Min(value = 0, message = "A quilometragem nao pode ser negativa")
        Integer quilometragem,

        @NotNull(message = "O tipo do veiculo e obrigatorio")
        TipoVeiculo tipo,

        @NotNull(message = "O status e obrigatorio")
        StatusVeiculo status) {
}
