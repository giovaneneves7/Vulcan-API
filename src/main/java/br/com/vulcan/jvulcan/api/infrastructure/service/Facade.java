package br.com.vulcan.jvulcan.api.infrastructure.service;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import br.com.vulcan.jvulcan.api.entity.banners.model.dto.CadastrarBannerDto;
import br.com.vulcan.jvulcan.api.entity.banners.service.IBannerService;
import br.com.vulcan.jvulcan.api.entity.cargo.model.Cargo;
import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;
import br.com.vulcan.jvulcan.api.entity.chibata.model.dto.request.CadastrarDadosChibataDto;
import br.com.vulcan.jvulcan.api.entity.chibata.service.IOlhoDaChibataService;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarCargoNovelDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarNovelDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelComCargoDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelComRankingTotalAtualizadoDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelResponseDto;
import br.com.vulcan.jvulcan.api.entity.novel.service.INovelService;

import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import br.com.vulcan.jvulcan.api.entity.post.service.IPostService;
import br.com.vulcan.jvulcan.api.entity.servidores.model.dto.CadastrarServidorAutorDto;
import br.com.vulcan.jvulcan.api.entity.servidores.service.IServidorAutorService;
import br.com.vulcan.jvulcan.api.infrastructure.exception.MessageNotSentException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;

import net.dv8tion.jda.api.JDA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    IServidorAutorService servidorAutorService;


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
     * Salva uma model na base de dados.
     * @param novelDto O DTO com dados da novel que será salva.
     * @return 'true' caso a model seja salva, 'false' caso contrário.
     */
    @Override
    public Novel salvarNovel(CadastrarNovelDto novelDto)
    {
        return this.novelService.salvar(novelDto);
    }

    /**
     * Atualiza o cargo das novels.
     * @param jda O objeto que permite a comunicação à API do Discord.
     * @return Lista com as novels na base de dados.
     */
    @Override
    public List<Novel> atualizarCargoDasNovels(JDA jda)
    {

        return this.novelService.atualizarCargo(jda);

    }

    /**
     * Cadastra ou atualiza o cargo de uma novel.
     * @param novelDto O DTO com os dados do cargo e da novel que será atualizada.
     * @return um DTO com os dados da novel que teve o cargo cadastrado.
     */
    @Override
    public NovelComCargoDto cadastrarCargoDaNovel(CadastrarCargoNovelDto novelDto) {
        return this.novelService.cadastrarCargo(novelDto);
    }

    /**
     * Atualiza as views das novels.
     *
     */
    @Override
    public void atualizarViewsDasNovels() {
        this.novelService.atualizarViews();
    }

    /**
     * Atualiza o ranking total das novels.
     *
     * @return lista com informações das novels que tiveram o ranking atualizado.
     */
    @Override
    public List<NovelComRankingTotalAtualizadoDto> atualizarRankingTotalDasNovels() {
        return this.novelService.atualizarRankingTotal();
    }

    /**
     * Deleta a model com o ID passado por parâmetro.
     * @param id O ID da model a ser deletada.
     */
    @Override
    public void deletarNovelPorId(long id)
    {

        this.novelService.deletar(id);

    }

    /**
     * Busca uma model pelo slug passado.
     * @param slug O slug da model.
     * @return A model com o slug passado por parâmetro, 'null' caso ela não exista.
     */
    @Override
    public <DTO extends NovelResponseDto> DTO buscarNovelPorSlug(String slug, Optional<String> filtro)
    {

        return this.novelService.buscarPorSlug(slug, filtro);

    }


    //====================={ BANNER - METODOS }=====================//

    /**
     * Adiciona um banner no banco de dados.
     *
     * @param bannerDto - Os dados do Banner que será cadastrado.
     */
    @Override
    public void salvarBanner(CadastrarBannerDto bannerDto)
    {

        this.bannerService.cadastrarBanner(bannerDto);

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
     * @param dadosChibataDto Os dados que serão cadastrados.
     */
    @Override
    public void cadastrarDadosChibata(CadastrarDadosChibataDto dadosChibataDto)
    {
        this.chibataService.cadastrarDadosChibata(dadosChibataDto);
    }

    /**
     * Lista todos os dados do Olho da Chibata.
     *
     * @param pageable (Opcional) o objeto pagebla, caso esteja presenta, retornará um lista pagindada.
     * @return Lista com dados do Olho da Chibata.
     */
    @Override
    public List<OlhoDaChibata> listarOlhoDaChibata(Optional<Pageable> pageable)
    {
        return this.chibataService.listarTodos(pageable);
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

    //====================={ SERVIDOR AUTOR - METODOS }=====================//
    /**
     * Cadastra um servidor de escritor na base de dados.
     *
     * @param servidorAutorDto O servidor de escritor a ser cadastrado.
     * @return informações do servidor cadastrado.
     */
    @Override
    public CadastrarServidorAutorDto cadastrarServidorAutor(CadastrarServidorAutorDto servidorAutorDto)
    {
        return this.servidorAutorService.cadastrarServidorAutor(servidorAutorDto);
    }
    //====================={ NOVEL VIEWS - METODOS }=====================//

}

