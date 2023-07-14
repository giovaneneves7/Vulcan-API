package br.com.vulcan.jvulcan.api.entity.post.service;

import br.com.vulcan.jvulcan.api.entity.chibata.model.PostsNaSemana;
import br.com.vulcan.jvulcan.api.entity.chibata.repository.PostsNaSemanaRepository;
import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;
import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;

import br.com.vulcan.jvulcan.api.entity.servidores.repository.ServidorAutorRepository;
import br.com.vulcan.jvulcan.api.entity.servidores.service.IServidorAutorService;
import br.com.vulcan.jvulcan.api.infrastructure.exception.MessageNotSentException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.IWebHookMessageDelivererService;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds.Author;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds.Embeds;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds.Footer;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds.MensagemJson;
import br.com.vulcan.jvulcan.api.infrastructure.util.Formatter;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@NoArgsConstructor
@PropertySource("classpath:application.properties")
public class PostService implements IPostService {

    private final String NOVEL_NOT_FOUND = "A model requisitada não existe na base de dados!";
    private final String MESSAGE_NOT_SENT = "A mensagem não foi enviada.";

    private int idUltimoPost = 0;

    @Value("${webhook_url}")
    private String webhookUrl;

    @Autowired
    PostsNaSemanaRepository postsNaSemanaRepository;

    @Autowired
    ServidorAutorRepository servidorAutorRepository;

    @Autowired
    NovelRepository novelRepository;

    @Autowired
    IWebHookMessageDelivererService webHookMessageDelivererService;

    @Autowired
    IServidorAutorService servidorAutorService;

    @PostConstruct
    public void init() {
        log.info("Conectado no WebHook: {}", webhookUrl);
    }

    /**
     * Envia uma embed via Webhook com informações de uma nova postagem no site.
     * 
     * @param post O post que será notificado via Webhook.
     */
    @Override
    public void notificarNovaPostagem(Post post) throws ObjectNotFoundException, MessageNotSentException {

        if (post.getPostId() != idUltimoPost) {

            try {


                Optional<Novel> optionalNovel = novelRepository.findByNome(post.getCategoria());

                String cargoMarcado = "ROLE_NOT_FOUND";
                String capaUrl = "https://vulcannovel.com.br";
                Novel novel = new Novel();
                var autor = "USERNAME_UNDEFINED";

                if (optionalNovel.isPresent()) {
                    novel = optionalNovel.get();

                    cargoMarcado = novel.getIdCargo();
                    capaUrl = new Formatter().formatarUrlDeCapa(novel.getCapa());
                    autor = novel.getAutor();

                } else {

                    throw new ObjectNotFoundException(NOVEL_NOT_FOUND);

                }

                MensagemJson mensagem = new MensagemJson("🗞 | <@&%s> <@&863456249873825812>".formatted(cargoMarcado),
                                                             new ArrayList<>(
                                                                                List.of(new Embeds(
                                                                                                    post.getTitulo(),
                                                                                                    post.getLink(),
                                                                                                    new Author(
                                                                                                           autor,
                                                                                                           post.getLinkAvatarAutor()
                                                                                                    ),
                                                                                                    "47615",
                                                                                                    new Footer(
                                                                                                        "⚡ Clique no título para ler o capítulo",
                                                                                                            capaUrl
                                                                                                    )
                                                                                        )
                                                                                )
                                                             )
                );

                if(!webHookMessageDelivererService.enviarMensagem(this.webhookUrl, mensagem))
                    log.error("Erro ao enviar a mensagem ao webhook");
                log.info("Mensagem enviada com sucesso!");

                //--+ Salva o novo na base de dados +--//
                Optional<PostsNaSemana> optionalPostsNaSemana = postsNaSemanaRepository.findByNovel(novel);
                optionalPostsNaSemana.ifPresent(postsNaSemana -> salvarPost(post, postsNaSemana));

                //--+ Notifica postagem em servidores de autores +--//
                servidorAutorService.notificarEmServidoresDeAutor(post);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            idUltimoPost = post.getPostId();

        }

    }

    /**
     * Salva uma nova postagem.
     *
     * @param post A postagem a ser salva.
     * @param postsNaSemana A lista de posts na semana relacionados à novel.
     */
    private void salvarPost(Post post, PostsNaSemana postsNaSemana){

        post.setDataPostagem(LocalDateTime.now());
        postsNaSemana.getPosts().add(post);
        postsNaSemana.setTotalPosts(postsNaSemana.getPosts().size());

        postsNaSemanaRepository.save(postsNaSemana);
    }
}
