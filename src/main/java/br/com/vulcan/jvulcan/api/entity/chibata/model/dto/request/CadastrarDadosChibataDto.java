package br.com.vulcan.jvulcan.api.entity.chibata.model.dto.request;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record CadastrarDadosChibataDto(

  @NotBlank
  @NotNull
  @Size(min = 1, message = "O nome da novel precisa ter ao menos um caracter")
  @JsonProperty(value = "novel")
  String novel,

  @NotBlank
  @NotNull
  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "A data de última postagem deve seguir o padrão 'yyy-MM-d HH-mm-ss'")
  @JsonProperty(value = "ultima_postagem")
  String ultimaPostagem)

{

  /**
   * Converte um record CadastrarDadosChibataDto para OlhoDaChibata.
   *
   * @param dadosChibata A instância de OlhoDaChibata que recebrá os dados.
   * @param novel A novel associada aos dados chibata.
   */
  public void converter(OlhoDaChibata dadosChibata, Optional<Novel> novel)
  {

    dadosChibata.setNovel(novel.orElseThrow(ObjectNotFoundException::new));
    dadosChibata.setUltimaPostagem(this.ultimaPostagem);

  }
}
