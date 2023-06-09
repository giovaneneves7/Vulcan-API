package br.com.vulcan.jvulcan.api.infrastructure.service;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import br.com.vulcan.jvulcan.api.entity.banners.service.IBannerService;
import br.com.vulcan.jvulcan.api.entity.cargo.model.Cargo;
import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;
import br.com.vulcan.jvulcan.api.entity.chibata.service.IOlhoDaChibataService;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.service.INovelService;

import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import br.com.vulcan.jvulcan.api.entity.post.service.IPostService;
import br.com.vulcan.jvulcan.api.infrastructure.exception.MessageNotSentException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;
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

    @Autowired
    IPostService postService;

    @Autowired
    IOlhoDaChibataService chibataService;


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

    /**
     * Atualiza o cargo das novels.
     * @param cargos A lista com cargos do servidor do discord da Vulcan.
     * @return Lista com as novels na base de dados.
     */
    @Override
    public List<Novel> atualizarCargoDasNovels(List<Cargo> cargos)
    {

        return this.novelService.atualizarCargo(cargos);

    }

    /**
     * Deleta a novel com o ID passado por parâmetro.
     * @param id O ID da novel a ser deletada.
     */
    @Override
    public void deletarNovelPorId(long id)
    {

        this.novelService.deletar(id);

    }

    /**
     * Busca uma novel pelo slug passado.
     * @param slug O slug da novel.
     * @return A novel com o slug passado por parâmetro, 'null' caso ela não exista.
     */
    @Override
    public Novel buscarNovelPorSlug(String slug)
    {

        return this.novelService.buscarPorSlug(slug);

    }


    //====================={ BANNER - METODOS }=====================//

    /**
     * Adiciona um banner no banco de dados.
     * @param banner O Banner que será cadastrado.
     * @return 'True' caso o banner seja cadastrado, 'false' caso contrário.
     */
    @Override
    public void salvarBanner(Banner banner)
    {

        this.bannerService.cadastrarBanner(banner);

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

    /**
     * Lista todos os banners da base de dados.
     *
     * @return lista com todos os banners cadastrados na base de dados.
     */
    @Override
    public List<Banner> listarTodosBanners()
    {
        return this.bannerService.listarTodosBanners();
    }

    /**
     * Deleta o banner que tiver o ID passado por parâmetro.
     *
     * @param id O ID do banner que será deletado.
     * @return as informações do banner que foi deletado.
     */
    @Override
    public Banner deletarBannerPorId(Long id)
    {
        return this.bannerService.deletarBannerPorId(id);
    }

    //====================={ POST - METODOS }=====================//

    /**
     * Envia uma embed via Webhook com informações de uma nova postagem no site.
     * @param post O post que será notificado via Webhook.
     */
    @Override
    public void notificarNovaPostagem(Post post) throws ObjectNotFoundException, MessageNotSentException
    {

        this.postService.notificarNovaPostagem(post);

    }

    //====================={ OLHO DA CHIBATA - METODOS }=====================//

    /**
     * Cadastra dados de ‘staffs’ e novels na base de dados.
     *
     * @param dadosChibata Os dados que serão cadastrados.
     */
    @Override
    public void cadastrarDadosChibata(OlhoDaChibata dadosChibata)
    {
        this.chibataService.cadastrarDadosChibata(dadosChibata);
    }

    /**
     * Lista todos os dados do Olho da Chibata.
     * @return Lista com dados do Olho da Chibata.
     */
    @Override
    public List<OlhoDaChibata> listarOlhoDaChibata()
    {
        return this.chibataService.listarTodos();
    }

    @Override
    public boolean atualizarDadosChibata(OlhoDaChibata dadosChibata)
    {
        return this.chibataService.atualizar(dadosChibata);
    }

    @Override
    public boolean descerAChibata()
    {
        return this.chibataService.cobrarBaianos();
    }

}

