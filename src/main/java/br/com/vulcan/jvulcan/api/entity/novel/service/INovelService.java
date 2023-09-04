package br.com.vulcan.jvulcan.api.entity.novel.service;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarCargoNovelDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarNovelDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelComCargoDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelComRankingTotalAtualizadoDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelResponseDto;
import net.dv8tion.jda.api.JDA;

import java.util.List;
import java.util.Optional;

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
     * Salva uma model na base de dados.
     * @param novelDto O DTO com dados da novel que será salva.
     * @return 'true' caso a model seja salva, 'false' caso contrário.
     */
    Novel salvar(CadastrarNovelDto novelDto);

    /**
     * Deleta a model com o ID passado por parâmetro.
     * @param id O ID da model a ser deletada.
     */
    void deletar(long id);

    /**
     * Busca uma model pelo slug passado.
     * @param slug O slug da model.
     * @return A model com o slug passado por parâmetro, 'null' caso ela não exista.
     */
    <DTO extends NovelResponseDto> DTO buscarPorSlug(String slug, Optional<String> filtro);

    /**
     * Atualiza o cargo das novels.
     * @param jda O objeto que permite a comunicação à API do Discord.
     * @return Lista com as novels na base de dados.
     */
    List<Novel> atualizarCargo(JDA jda);

    /**
     * Cadastra ou atualiza o cargo de uma novel.
     * @param novelDto O DTO com os dados do cargo e da novel que será atualizada.
     * @return um DTO com os dados da novel que teve o cargo cadastrado.
     */
    NovelComCargoDto cadastrarCargo(CadastrarCargoNovelDto novelDto);


    /**
     * Atualiza as views das novels.
     *
     */
    void atualizarViews();

    /**
     * Atualiza o ranking total das novels.
     *
     * @return lista com informações das novels que tiveram o ranking atualizado.
     */
    List<NovelComRankingTotalAtualizadoDto> atualizarRankingTotal();

}
