package br.com.vulcan.jvulcan.api.entity.servidores.model.dto;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.NovelServidorAutorDto;
import br.com.vulcan.jvulcan.api.entity.servidores.model.ServidorAutor;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record CadastrarServidorAutorDto(@NotNull(message = "O webhook é obrigatório") @NotBlank @Size(min = 5, message = "O webhook deve ter mais de 5 caracteres") @JsonProperty(value = "webhook") String webhook,
                                        @NotNull(message = "A mensagem personalizada é obrigatória") @NotBlank @Size(min = 5, message = "A mensagem deve ter mais de 5 caracteres") @JsonProperty(value = "mensagem") String mensagem,
                                        @JsonProperty(value = "id_cargo") String idCargo,
                                        @NotNull(message = "A novel é obrigatória") @JsonProperty(value = "novel") NovelServidorAutorDto novel) {

    void converterParaServidorAutor(ServidorAutor servidorAutor, Optional<Novel> novel)
    {

        servidorAutor.setNovel(novel.orElseThrow(ObjectNotFoundException::new));
        servidorAutor.setWebhook(this.webhook);
        servidorAutor.setMensagem(this.mensagem);
        servidorAutor.setIdCargo(this.idCargo);

    }
}
