package br.com.vulcan.jvulcan.api.entity.post.service;

import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;
import br.com.vulcan.jvulcan.api.entity.post.model.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

@Service
public class PostService implements IPostService
{
    private final String WEBHOOK_URL = "https://discord.com/api/webhooks/1109128565141798943/yLKCpYd7YBJhpAfHZ5jR8WRYks9t6IGLWoYcXIIhV2xbZmObnwGBpD1x9L6Wmb44g_HM";
    @Autowired
    NovelRepository novelRepository;

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
                                                .uri(URI.create(WEBHOOK_URL))
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
