package br.com.vulcan.jvulcan.api.entity.cargo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Cargo
{
    @JsonProperty("nome_cargo")
    private String nome;
    @JsonProperty("id_cargo")
    private String id;

}
