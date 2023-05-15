package br.com.vulcan.jvulcan.api.infrastructure.service;

import br.com.vulcan.jvulcan.api.banners.model.Banner;
import br.com.vulcan.jvulcan.api.banners.service.IBannerService;
import br.com.vulcan.jvulcan.api.novel.model.Novel;
import br.com.vulcan.jvulcan.api.novel.service.INovelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Facade implements IFacade
{

    @Autowired
    INovelService novelService;

    @Autowired
    IBannerService bannerService;

    //====================={ NOVEL - METODOS }=====================//
    /**
     * Retorna uma lista com todas as novels.
     * @return uma lista com todas as novels.
     */
    @Override
    public List<Novel> listarTodasNovels()
    {
        return this.novelService.listarTodas();
    }

    /**
     * Retorna uma lista com todas as novels com a nacionalidade especificada.
     * @return uma lista com todas as novels com a nacionalidade especificada.
     */
    @Override
    public List<Novel> listarTodasNovels(String nacionalidade)
    {
        return this.novelService.listarTodas(nacionalidade);
    }

    /**
     * Salva uma novel na base de dados.
     * @param novel A novel que será salva.
     * @return 'true' caso a novel seja salva, 'false' caso contrário.
     */
    @Override
    public boolean salvarNovel(Novel novel)
    {
        return this.novelService.salvar(novel);
    }


    //====================={ BANNER - METODOS }=====================//

    /**
     * Adiciona um banner no banco de dados.
     * @param banner O Banner que será cadastrado.
     * @return 'True' caso o banner seja cadastrado, 'false' caso contrário.
     */
    @Override
    public boolean salvarBanner(Banner banner)
    {

        return this.bannerService.salvar(banner);

    }

    /**
     * Retorna um banner aleatório da base de dados.
     * @return um banner aleatório.
     */
    @Override
    public Banner pegarBannerAleatorio()
    {

        return this.bannerService.pegarBannerAleatorio();

    }
}
