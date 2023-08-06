package br.com.vulcan.jvulcan.api.entity.novel.service;

import br.com.vulcan.jvulcan.api.entity.cargo.model.Cargo;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarNovelDto;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

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
    Novel buscarPorSlug(String slug);

    /**
     * Atualiza o cargo das novels.
     * @param jda O objeto que permite a comunicação à API do Discord.
     * @return Lista com as novels na base de dados.
     */
    List<Novel> atualizarCargo(JDA jda);

    void atualizarViews(String slug);
}
