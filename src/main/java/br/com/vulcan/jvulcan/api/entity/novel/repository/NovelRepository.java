package br.com.vulcan.jvulcan.api.entity.novel.repository;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long>
{

    /**
     * Retorna lista de novels com a nacionalidade especificada.
     * @param nacionalidade A nacionalidade da model a ser buscada.
     * @return lista de model com a nacionalidade especificada.
     */
    List<Novel> findByNacionalidade(String nacionalidade);

    /**
     * Retorna uma model com o slug passado por parâmetro.
     * @param slug O slug da model a ser buscada.
     * @return model com o slug especificado.
     */
    Optional<Novel> findBySlug(String slug);

    /**
     * Retorna model com o nome passado por parâmetro.
     * @param nome O nome da model a ser buscado.
     * @return model com o nome especificado.
     */
    Optional<Novel> findByNome(String nome);
}
