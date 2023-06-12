package br.com.vulcan.jvulcan.api.entity.banners.model.dto;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto
{

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "link")
    private String link;

    @JsonProperty(value = "url_redirecionamento")
    private String urlRedirecionamento;

    public BannerDto(Banner banner)
    {
        this.nome = banner.getNome();
        this.link = banner.getLink();
        this.urlRedirecionamento = banner.getUrlRedirecionamento();

    }

    public static List<BannerDto> converterLista(List<Banner> banners)
    {
        return banners.stream().map(BannerDto::new)
                .collect(Collectors.toList());
    }
}
