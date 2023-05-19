package br.com.vulcan.jvulcan.api.entity.post.service;

import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;
import br.com.vulcan.jvulcan.api.entity.post.model.Post;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

@Service
@NoArgsConstructor
@PropertySource("classpath:application.properties")
public class PostService implements IPostService
{

    @Value("${webhook_url}")
    private String webhookUrl;

    @Autowired
    NovelRepository novelRepository;

    @PostConstruct
    public void init()
    {
        System.out.println("Conectado no WebHook: ".concat(webhookUrl));
    }

    /**
     * Envia uma embed via Webhook com informações de uma nova postagem no site.
     * @param post O post que será notificado via Webhook.
     */
    @Override
    public void notificarNovaPostagem(Post post) {

        try{

            String mensagemJson =
            """
                    {
                        "content:" : "",
                        "embeds" : [
                                        {
                                            "title" : "%s",
                                            "url" : "%s",
                                            "author" : {
                                                "name" : "%s",
                                                "icon_url" : "%s"
                                            }
                                            
                                        }
                                    ]
                    }
            """.formatted(post.getTitulo(), post.getLink(), post.getAutor(), post.getLinkAvatarAutor());

            HttpClient cliente  = HttpClient.newHttpClient();
            HttpRequest requisicao = HttpRequest.newBuilder()
                                                .uri(URI.create(this.webhookUrl))
                                                .header("Content-Type", "application/json")
                                                .POST(HttpRequest.BodyPublishers.ofString(mensagemJson, StandardCharsets.UTF_8))
                                                .build();

            HttpResponse<String> response = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204) {
                System.out.println("Mensagem enviada com sucesso!");
            } else {
                System.out.println("Erro ao enviar mensagem: " + response.statusCode() + " " + response.body());
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }
}
