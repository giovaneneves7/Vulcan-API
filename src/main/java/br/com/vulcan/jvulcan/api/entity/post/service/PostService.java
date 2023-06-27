package br.com.vulcan.jvulcan.api.entity.post.service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@NoArgsConstructor
@PropertySource("classpath:application.properties")
public class PostService implements IPostService {

    private final String NOVEL_NOT_FOUND = "A novel requisitada não existe na base de dados!";
    private final String MESSAGE_NOT_SENT = "A mensagem não foi enviada.";

    private int idUltimoPost = 0;

    @Value("${webhook_url}")
    private String webhookUrl;

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
        System.out.println("Conectado no WebHook: ".concat(webhookUrl));
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
                String capaUrl = "https://web.postman.co/workspace/My-Workspace~1108489f-00b6-4bb8-8ecd-c72277a45a1d/request/27286299-76eca593-a7dc-4afe-a73b-7d88aa307910";
                var autor = "USERNAME_UNDEFINED";

                if (optionalNovel.isPresent()) {
                    Novel novel = optionalNovel.get();

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

                servidorAutorService.notificarEmServidoresDeAutor(post);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            idUltimoPost = post.getPostId();

        }

    }
}
