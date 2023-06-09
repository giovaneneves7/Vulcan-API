package br.com.vulcan.jvulcan.api.entity.banners.service;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;

import java.util.List;

public interface IBannerService
{

    /**
     * Adiciona um banner no banco de dados.
     * @param banner -O Banner que será cadastrado.
     */
    void cadastrarBanner(Banner banner);

    /**
     * Retorna um banner aleatório da base de dados.
     * @return um banner aleatório.
     */
    Banner pegarBannerAleatorio();

    /**
     * Lista todos os banners da base de dados.
     *
     * @return lista com todos os banners cadastrados na base de dados, ou nulo, caso não haja registros.
     */
    List<Banner> listarTodosBanners();

    /**
     * Deleta o banner que tiver o ID passado por parâmetro.
     *
     * @param id O ID do banner que será deletado.
     * @return as informações do banner que foi deletado.
     */
    Banner deletarBannerPorId(Long id);
}
