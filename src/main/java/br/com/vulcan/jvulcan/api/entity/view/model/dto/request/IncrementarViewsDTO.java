package br.com.vulcan.jvulcan.api.entity.view.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IncrementarViewsDTO(@JsonProperty
                                  @NotNull
                                  @NotBlank
                                  String categoria)
{

}
