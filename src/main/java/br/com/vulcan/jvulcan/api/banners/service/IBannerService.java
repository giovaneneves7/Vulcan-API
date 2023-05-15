package br.com.vulcan.jvulcan.api.banners.service;

import br.com.vulcan.jvulcan.api.banners.model.Banner;

public interface IBannerService
{

    /**
     * Retorna um banner aleatório da base de dados.
     * @return um banner aleatório.
     */
    Banner pegarBannerAleatorio();

}
