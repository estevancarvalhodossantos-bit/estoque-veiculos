package br.com.unifecaf.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ModeloRequest(
        @NotBlank(message = "O nome do modelo e obrigatorio")
        @Size(max = 80, message = "O nome do modelo deve ter no maximo 80 caracteres")
        String nome,

        @NotNull(message = "A marca e obrigatoria")
        Long marcaId) {
}
