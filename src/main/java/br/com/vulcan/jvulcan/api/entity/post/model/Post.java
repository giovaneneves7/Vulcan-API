package br.com.vulcan.jvulcan.api.entity.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class Post
{

    @JsonProperty("nome_novel")
    private String nomeNovel;

    @JsonProperty("categoria")
    private String categoria;

    @JsonProperty("titulo_postagem")
    private String titulo;

    @JsonProperty("autor_postagem")
    private String autor;

    @JsonProperty("link_postagem")
    private String link;

    @JsonProperty("link_avatar_autor")
    private String linkAvatarAutor;

    private String cargoMarcado;
}
