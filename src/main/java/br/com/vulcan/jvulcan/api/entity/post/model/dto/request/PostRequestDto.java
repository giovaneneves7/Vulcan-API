package br.com.vulcan.jvulcan.api.entity.post.model.dto.request;

import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequestDto(@JsonProperty("post_id")
                             @NotNull
                             int postId,
                            @JsonProperty("nome_novel")
                            @NotNull
                            @NotBlank
                            String nomeNovel,
                            @JsonProperty("categoria")
                            @NotNull
                            @NotBlank
                            String categoria,
                            @JsonProperty("titulo_postagem")
                            String titulo,
                            @JsonProperty("autor_postagem")
                            String autor,
                            @JsonProperty("link_postagem")
                            @NotNull
                            @NotBlank
                            String link,
                            @JsonProperty("link_avatar_autor")
                            @NotNull
                            @NotBlank
                            String linkAvatarAutor){

    /**
     * Converte o DTO para Objeto Post.
     *
     * @return um objeto Post com os dados do DTO.
     */
    public Post toPost(){

        return new Post(this.postId, this.nomeNovel, this.categoria,
                        this.titulo, this.autor, this.link, this.linkAvatarAutor);

    }

}
