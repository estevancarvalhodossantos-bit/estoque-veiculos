package br.com.unifecaf.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MarcaRequest(
        @NotBlank(message = "O nome da marca e obrigatorio")
        @Size(max = 60, message = "O nome da marca deve ter no maximo 60 caracteres")
        String nome) {
}
