package br.com.vulcan.jvulcan.api.entity.chibata.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastrarDadosChibata(

  @NotBlank
  @NotNull
  @Size(min = 1, message = "O nome da novel precisa ter ao menos um caracter")
  @JsonProperty(value = "novel")
  String novel,

  @NotBlank
  @NotNull
  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "A data de última postagem deve seguir o padrão 'yyy-MM-d HH-mm-ss'")
  @JsonProperty(value = "ultima_postagem")
  String ultimaPostagem,

  @NotBlank
  @Size(min = 1, message = "O nome do membro da staff precisa ter no minimo um caracter")
  @NotNull
  @JsonProperty(value = "autor_ou_tradutor")
  String autorOuTradutor)
{

}
