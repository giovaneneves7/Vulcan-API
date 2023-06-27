package br.com.vulcan.jvulcan.api.entity.servidores.service;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;
import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import br.com.vulcan.jvulcan.api.entity.servidores.model.ServidorAutor;
import br.com.vulcan.jvulcan.api.entity.servidores.model.dto.CadastrarServidorAutorDto;
import br.com.vulcan.jvulcan.api.entity.servidores.repository.ServidorAutorRepository;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.IWebHookMessageDelivererService;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds.Author;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds.Embeds;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds.Footer;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds.MensagemJson;
import br.com.vulcan.jvulcan.api.infrastructure.util.Formatter;
import kotlin.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ServidorAutorService implements IServidorAutorService{

    @Autowired
    ServidorAutorRepository servidorAutorRepository;
    @Autowired
    NovelRepository novelRepository;

    @Autowired
    IWebHookMessageDelivererService webHookMessageDelivererService;

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

    /**
     * Notifica uma nova postagem em servidores de autores da Vulcan.
     *
     * @return 'true' caso a mensagem seja enviada com sucesso, 'false' caso não.
     */
    @Override
    public boolean notificarEmServidoresDeAutor(Post post) {

        List<ServidorAutor> listaServidores = servidorAutorRepository.findAll();

        if(listaServidores.isEmpty()) return false;

        listaServidores.stream()
                .map(s -> {
                    Optional<Novel> nOptional = novelRepository.findByNome(s.getNovel().getNome());
                    return nOptional.map( n -> new Pair<>(s, n));
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(pair -> {

                    if(pair.getFirst().getNovel().getNome().equals(post.getCategoria()))
                    {
                        MensagemJson mensagem = new MensagemJson(pair.getFirst().getMensagem().concat(" <@&").concat(pair.getFirst().getIdCargo()).concat(">"),
                                new ArrayList<>(
                                        List.of(new Embeds(
                                                        post.getTitulo(),
                                                        post.getLink(),
                                                        new Author(
                                                                pair.getSecond().getAutor(),
                                                                post.getLinkAvatarAutor()
                                                        ),
                                                        "47615",
                                                        new Footer(
                                                                "⚡ Clique no título para ler o capítulo",
                                                                new Formatter().formatarUrlDeCapa(pair.getSecond().getCapa())
                                                        )
                                                )
                                        )
                                )
                        );

                        webHookMessageDelivererService.enviarMensagem(pair.getFirst().getWebhook(), mensagem);
                        log.info("Mensagem enviada a um servidor de autores");

                    }

                });


        return true;
    }
}
