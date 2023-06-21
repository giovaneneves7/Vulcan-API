package br.com.vulcan.jvulcan.api.entity.servidores.service;

import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;
import br.com.vulcan.jvulcan.api.entity.servidores.model.ServidorAutor;
import br.com.vulcan.jvulcan.api.entity.servidores.model.dto.CadastrarServidorAutorDto;
import br.com.vulcan.jvulcan.api.entity.servidores.repository.ServidorAutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ServidorAutorService implements IServidorAutorService{

    @Autowired
    ServidorAutorRepository servidorAutorRepository;
    @Autowired
    NovelRepository novelRepository;

    /**
     * Cadastra um servidor de autor na base de dados.
     *
     * @param servidorAutorDto O servidor de autor a ser cadastrado.
     * @return informações do servidor cadastrado.
     */
    @Override
    public CadastrarServidorAutorDto cadastrarServidorAutor(CadastrarServidorAutorDto servidorAutorDto) {

        ServidorAutor servidorAutor = new ServidorAutor();

        servidorAutorDto.converterParaServidorAutor(servidorAutor,
                                                    novelRepository.findByNome(servidorAutorDto.novel().nome()));

        servidorAutorRepository.save(servidorAutor);

        return servidorAutorDto;
    }

    @Override
    public Page<ServidorAutor> listarTodosServidores() {
        return null;
    }
}
