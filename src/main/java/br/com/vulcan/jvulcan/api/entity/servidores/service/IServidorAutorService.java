package br.com.vulcan.jvulcan.api.entity.servidores.service;

import br.com.vulcan.jvulcan.api.entity.servidores.model.ServidorAutor;
import br.com.vulcan.jvulcan.api.entity.servidores.model.dto.CadastrarServidorAutorDto;
import org.springframework.data.domain.Page;

public interface IServidorAutorService
{


    /**
     * Cadastra um servidor de autor na base de dados.
     *
     * @param servidorAutorDto O servidor de autor a ser cadastrado.
     * @return informações do servidor cadastrado.
     */
    CadastrarServidorAutorDto cadastrarServidorAutor(CadastrarServidorAutorDto servidorAutorDto);

    /**
     * Retorna uma página com os servidores cadastrados na base de dados.
     *
     * @return lista com os servidores na base de dados.
     */
    Page<ServidorAutor> listarTodosServidores();
}
