package br.com.vulcan.jvulcan.api.entity.view.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ResponseData {

    @JsonProperty(value = "category")
    String category;

    @JsonProperty(value  ="total_views")
    Long totalViews;

}
