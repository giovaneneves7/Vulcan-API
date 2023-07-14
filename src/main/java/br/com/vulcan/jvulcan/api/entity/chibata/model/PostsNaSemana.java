package br.com.vulcan.jvulcan.api.entity.chibata.model;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "posts_na_semana")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsNaSemana {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Novel novel;

    private int totalPosts;

    @OneToMany
    private List<Post> posts;

}
