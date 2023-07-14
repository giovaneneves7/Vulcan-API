package br.com.vulcan.jvulcan.api.entity.view.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IncrementarViewsDto(@JsonProperty(value = "categoria")
                                  String categoria,
                                  @JsonProperty(value = "total_views")
                                  long totalViews){
}
