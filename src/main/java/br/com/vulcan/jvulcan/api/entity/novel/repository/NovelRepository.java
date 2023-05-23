package br.com.vulcan.jvulcan.api.entity.novel.repository;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long>
{
    List<Novel> findByNacionalidade(String nacionalidade);
    Optional<Novel> findBySlug(String slug);
    Optional<Novel> findByNome(String nome);
}
