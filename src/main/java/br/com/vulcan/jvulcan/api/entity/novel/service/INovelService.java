package br.com.vulcan.jvulcan.api.entity.novel.service;

import br.com.vulcan.jvulcan.api.entity.cargo.model.Cargo;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;

import java.util.List;

public interface INovelService
{

    /**
     * Retorna uma lista com todas as novels.
     * @return uma lista com todas as novels.
     */
    List<Novel> listarTodas();

    /**
     * Retorna uma lista com todas as novels com a nacionalidade especificada.
     * @return uma lista com todas as novels com a nacionalidade especificada.
     */
    List<Novel> listarTodas(String nacionalidade);

    /**
     * Salva uma novel na base de dados.
     * @param novel A novel que será salva.
     * @return 'true' caso a novel seja salva, 'false' caso contrário.
     */
    boolean salvar(Novel novel);

    /**
     * Deleta a novel com o ID passado por parâmetro.
     * @param id O ID da novel a ser deletada.
     */
    void deletar(long id);

    /**
     * Busca uma novel pelo slug passado.
     * @param slug O slug da novel.
     * @return A novel com o slug passado por parâmetro, 'null' caso ela não exista.
     */
    Novel buscarPorSlug(String slug);

    /**
     * Atualiza o cargo das novels.
     * @param cargos A lista com cargos do servidor do discord da Vulcan.
     * @return Lista com as novels na base de dados.
     */
    List<Novel> atualizarCargo(List<Cargo> cargos);

    void atualizarViews(String slug);
}
