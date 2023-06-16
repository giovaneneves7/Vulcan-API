package br.com.vulcan.jvulcan.api.entity.banners.model.dto;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;
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

    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 e 50 caracteres")
    @JsonProperty(value = "nome")
    private String nome;

    @Size(min = 5, message = "O link é muito curto")
    @JsonProperty(value = "link")
    private String link;

    @Size(min = 5, message = "O url de redirecionamento é muito curto")
    @JsonProperty(value = "url_redirecionamento")
    private String urlRedirecionamento;

    public BannerDto(Banner banner)
    {
        this.nome = banner.getNome();
        this.link = banner.getLink();
        this.urlRedirecionamento = banner.getUrlRedirecionamento();

    }

    /**
     * Converte uma lista de objetos banners em bannersDTO
     *
     * @param banners A lista de banners que será convertida.
     * @return Lista de objetos convertidos para bannerDto.
     */
    public static List<BannerDto> converterLista(List<Banner> banners)
    {
        return banners.stream().map(BannerDto::new)
                .collect(Collectors.toList());
    }
}
