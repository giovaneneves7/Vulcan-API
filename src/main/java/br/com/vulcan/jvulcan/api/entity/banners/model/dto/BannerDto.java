package br.com.vulcan.jvulcan.api.entity.banners.model.dto;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto
{

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "link")
    private String link;

    @JsonProperty(value = "indice_novel")
    private String indice_novel;


    public BannerDto(Banner banner)
    {
        this.nome = banner.getNome();
        this.link = banner.getLink();
        this.indice_novel = banner.getNovel().getIndice();

    }
}
