package br.com.vulcan.jvulcan.api.entity.novel.repository;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long>
{

    /**
     * Retorna lista de novels com a nacionalidade especificada.
     * @param nacionalidade A nacionalidade da novel a ser buscada.
     * @return lista de novel com a nacionalidade especificada.
     */
    List<Novel> findByNacionalidade(String nacionalidade);

    /**
     * Retorna uma novel com o slug passado por parâmetro.
     * @param slug O slug da novel a ser buscada.
     * @return novel com o slug especificado.
     */
    Optional<Novel> findBySlug(String slug);

    /**
     * Retorna novel com o nome passado por parâmetro.
     * @param nome O nome da novel a ser buscado.
     * @return novel com o nome especificado.
     */
    Optional<Novel> findByNome(String nome);

    /**
     * Retorna uma lista de novels com cargo do discord nulo.
     *
     * @return lista de novels que posssuem o cargo do discord nulo.
     */
    @Query(value = "SELECT n FROM Novel n WHERE n.idCargo IS NULL")
    List<Novel> findNovelWithNullIdCargo();
}
