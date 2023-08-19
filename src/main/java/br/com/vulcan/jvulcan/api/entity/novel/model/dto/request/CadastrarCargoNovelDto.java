package br.com.vulcan.jvulcan.api.entity.novel.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarCargoNovelDto (
        @JsonProperty(value = "cargo")
        @NotNull
        @NotBlank
        String cargo,

        @JsonProperty(value = "id")
        @NotNull
        String id
){

}
