package br.com.vulcan.jvulcan.api.entity.view.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class View {

    @JsonProperty(value  ="success")
    Boolean success;

    @JsonProperty(value ="data")
    ResponseData data;

}
