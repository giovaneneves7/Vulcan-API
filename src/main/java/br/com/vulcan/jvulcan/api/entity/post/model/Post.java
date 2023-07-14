package br.com.vulcan.jvulcan.api.entity.post.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private int postId;

    @Column(name = "nome_novel")
    private String nomeNovel;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "titulo_postagem")
    private String titulo;

    @Column(name = "autor_postagem")
    private String autor;

    @Column(name = "link_postagem")
    private String link;

    @Column(name = "link_avatar_autor")
    private String linkAvatarAutor;

    @Column(name = "data_postagem")
    private LocalDateTime dataPostagem;

    public Post(int postId, String nomeNovel, String categoria, String titulo, String autor, String link, String linkAvatarAutor) {
        this.postId = postId;
        this.nomeNovel = nomeNovel;
        this.categoria = categoria;
        this.titulo = titulo;
        this.autor = autor;
        this.link = link;
        this.linkAvatarAutor = linkAvatarAutor;
    }
}
