package br.com.vulcan.jvulcan.api.entity.chibata.repository;

import br.com.vulcan.jvulcan.api.entity.chibata.model.PostsNaSemana;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostsNaSemanaRepository extends JpaRepository<PostsNaSemana, UUID> {

    public Optional<PostsNaSemana> findByNovel(Novel novel);

}
