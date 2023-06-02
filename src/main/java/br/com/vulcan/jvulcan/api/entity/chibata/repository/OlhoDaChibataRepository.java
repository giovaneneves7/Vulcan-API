package br.com.vulcan.jvulcan.api.entity.chibata.repository;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface OlhoDaChibataRepository extends JpaRepository<OlhoDaChibata, Long>
{
    Optional<List<OlhoDaChibata>> findByNovel(String novel);

}
