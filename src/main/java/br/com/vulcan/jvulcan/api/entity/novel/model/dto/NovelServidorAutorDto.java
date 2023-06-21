package br.com.vulcan.jvulcan.api.entity.novel.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NovelServidorAutorDto(@NotNull(message = "O nome da novel é obrigatório")
                                    @NotBlank
                                    @Size(min = 5, message = "O nome da novel deve der mais de 5 caracteres") String nome) {
}
