package br.com.vulcan.jvulcan.api.entity.view.repository;

import br.com.vulcan.jvulcan.api.entity.view.model.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface NovelViewsRepository extends JpaRepository<View, UUID> {

    Optional<View> findByCategoria(String categoria);

}
