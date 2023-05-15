package br.com.vulcan.jvulcan.api.infrastructure.service;

import br.com.vulcan.jvulcan.api.banners.model.Banner;
import br.com.vulcan.jvulcan.api.novel.model.Novel;

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
}
