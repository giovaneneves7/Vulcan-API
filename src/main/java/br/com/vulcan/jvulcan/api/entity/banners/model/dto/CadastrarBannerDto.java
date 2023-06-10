package br.com.vulcan.jvulcan.api.entity.banners.model.dto;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastrarBannerDto
{

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "link")
    private String link;

    @JsonProperty(value = "novel")
    private Novel novel;

}
