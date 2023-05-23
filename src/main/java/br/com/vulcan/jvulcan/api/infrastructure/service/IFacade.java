package br.com.vulcan.jvulcan.api.infrastructure.service;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import br.com.vulcan.jvulcan.api.entity.cargo.model.Cargo;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import br.com.vulcan.jvulcan.api.infrastructure.exception.NovelNotFoundException;

import java.util.List;

public interface IFacade {

    //=========================={ NOVELS }==========================//
    /**
     * Retorna uma lista com todas as novels.
     * @return uma lista com todas as novels.
     */
    List<Novel> listarTodasNovels();

    /**
     * Retorna uma lista com todas as novels com a nacionalidade especificada.
     * @return uma lista com todas as novels com a nacionalidade especificada.
     */
    List<Novel> listarTodasNovels(String nacionalidade);

    /**
     * Salva uma novel na base de dados.
     * @param novel A novel que será salva.
     * @return 'true' caso a novel seja salva, 'false' caso contrário.
     */
    boolean salvarNovel(Novel novel);

    /**
     * Atualiza o cargo das novels.
     * @param cargos A lista com cargos do servidor do discord da Vulcan.
     * @return Lista com as novels na base de dados.
     */
    List<Novel> atualizarCargoDasNovels(List<Cargo> cargos);

    void deletarNovelPorId(long id);
    /**
     * Busca uma novel pelo slug passado.
     * @param slug O slug da novel.
     * @return A novel com o slug passado por parâmetro, 'null' caso ela não exista.
     */
    Novel buscarNovelPorSlug(String slug);

    //=========================={ BANNERS }==========================//

    /**
     * Adiciona um banner no banco de dados.
     * @param banner O Banner que será cadastrado.
     * @return 'True' caso o banner seja cadastrado, 'false' caso contrário.
     */
    boolean salvarBanner(Banner banner);

    /**
     * Retorna um banner aleatório da base de dados.
     * @return um banner aleatório.
     */
    Banner pegarBannerAleatorio();

    //=========================={ POST }==========================//

    /**
     * Envia uma embed via Webhook com informações de uma nova postagem no site.
     * @param post O post que será notificado via Webhook.
     */
    void notificarNovaPostagem(Post post) throws NovelNotFoundException;


}
