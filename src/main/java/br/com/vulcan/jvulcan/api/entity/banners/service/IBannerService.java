package br.com.vulcan.jvulcan.api.entity.banners.service;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;

public interface IBannerService
{

    /**
     * Adiciona um banner no banco de dados.
     * @param banner -O Banner que será cadastrado.
     * @return 'True' caso o banner seja cadastrado, 'false' caso contrário.
     */
    boolean salvar(Banner banner);

    /**
     * Retorna um banner aleatório da base de dados.
     * @return um banner aleatório.
     */
    Banner pegarBannerAleatorio();

}
