package br.com.vulcan.jvulcan.api.infrastructure.service;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import br.com.vulcan.jvulcan.api.entity.banners.model.dto.CadastrarBannerDto;
import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;
import br.com.vulcan.jvulcan.api.entity.chibata.model.dto.request.CadastrarDadosChibataDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarCargoNovelDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarNovelDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelComCargoDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelComRankingTotalAtualizadoDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelResponseDto;
import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import br.com.vulcan.jvulcan.api.entity.servidores.model.dto.CadastrarServidorAutorDto;
import br.com.vulcan.jvulcan.api.infrastructure.exception.MessageNotSentException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;

import net.dv8tion.jda.api.JDA;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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
     * Pega informações de uma novel na base de dados pelo Slug passado por parâmetro.
     *
     * @param slug O slug da novel que será buscada na base de dados.
     * @param filtros Os filtros com as informações da novel que devem ser retornadas na resposta.
     * @return um DTO com informações da novel encontrada na base de dados.
     */
    NovelResponseDto pegarNovelPorSlug(String slug, List<String> filtros);

    /**
     * Salva uma model na base de dados.
     * @param novelDto O DTO com dados da novel que será salva.
     * @return 'true' caso a model seja salva, 'false' caso contrário.
     */
    Novel salvarNovel(CadastrarNovelDto novelDto);

    /**
     * Atualiza o cargo das novels.
     * @param jda O objeto que permite a comunicação à API do Discord.
     * @return Lista com as novels na base de dados.
     */
    List<Novel> atualizarCargoDasNovels(JDA jda);

    /**
     * Cadastra ou atualiza o cargo de uma novel.
     * @param novelDto O DTO com os dados do cargo e da novel que será atualizada.
     * @return um DTO com os dados da novel que teve o cargo cadastrado.
     */
    NovelComCargoDto cadastrarCargoDaNovel(CadastrarCargoNovelDto novelDto);

    /**
     * Atualiza as views das novels.
     *
     */
    void atualizarViewsDasNovels();

    /**
     * Atualiza o ranking total das novels.
     *
     * @return lista com informações das novels que tiveram o ranking atualizado.
     */
    List<NovelComRankingTotalAtualizadoDto> atualizarRankingTotalDasNovels();

    /**
     * Deleta a model com o ID passado por parâmetro.
     * @param id O ID da model a ser deletada.
     */
    void deletarNovelPorId(long id);

    /**
     * Busca uma novel pelo slug passado.
     * @param slug O slug da novel.
     * @return A model com o slug passado por parâmetro, 'null' caso ela não exista.
     */
    <DTO extends NovelResponseDto> DTO buscarNovelPorSlug(String slug, Optional<String> filtro);

    //=========================={ BANNERS }==========================//

    /**
     * Adiciona um banner no banco de dados.
     *
     * @param bannerDto - Os dados do Banner que será cadastrado.
     */
    void salvarBanner(CadastrarBannerDto bannerDto);

    /**
     * Retorna um banner aleatório da base de dados.
     * @return um banner aleatório.
     */
    Banner pegarBannerAleatorio();

    /**
     * Lista todos os banners da base de dados.
     *
     * @return lista com todos os banners cadastrados na base de dados.
     */
    List<Banner> listarTodosBanners();

    /**
     * Deleta o banner que tiver o ID passado por parâmetro.
     *
     * @param id O ID do banner que será deletado.
     * @return as informações do banner que foi deletado.
     */
     Banner deletarBannerPorId(Long id);

    //=========================={ POST }==========================//

    /**
     * Envia uma embed via Webhook com informações de uma nova postagem no site.
     * @param post O post que será notificado via Webhook.
     */
    void notificarNovaPostagem(Post post) throws ObjectNotFoundException, MessageNotSentException;

    //=========================={ OLHO DA CHIBATA }==========================//

    /**
     * Cadastra dados de ‘staffs’ e novels na base de dados.
     * @param dadosChibataDto Os dados que serão cadastrados.
     */
    void cadastrarDadosChibata(CadastrarDadosChibataDto dadosChibataDto);

    /**
     * Lista todos os dados do Olho da Chibata.
     *
     * @param pageable (Opcional) o objeto pagebla, caso esteja presenta, retornará um lista pagindada.
     * @return Lista com dados do Olho da Chibata.
     */
    List<OlhoDaChibata> listarOlhoDaChibata(Optional<Pageable> pageable);

    /**
     * Atualiza os dados de um registro existente.
     *
     * @param dadosChibata Os dados a serem atualizados
     * @return 'true' caso os dados sejam atualizados com sucesse, 'false' caso contrário.
     */
    boolean atualizarDadosChibata(OlhoDaChibata dadosChibata);

    /**
     * Cobra membros com 7 dias sem postagem.
     * @return 'true' caso a cobrança seja realizada com sucesso, 'false' caso não.
     */
    boolean descerAChibata();

    //=========================={ SERVIDOR AUTOR }==========================//
    /**
     * Cadastra um servidor de escritor na base de dados.
     *
     * @param servidorAutorDto O servidor de escritor a ser cadastrado.
     * @return informações do servidor cadastrado.
     */
    CadastrarServidorAutorDto cadastrarServidorAutor(CadastrarServidorAutorDto servidorAutorDto);

    //====================={ NOVEL VIEWS - METODOS }=====================//

}
