package br.com.vulcan.jvulcan.api.entity.banners.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastrarBannerDto
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

}
