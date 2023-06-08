package br.com.vulcan.jvulcan.api.infrastructure.service.discord;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class WebHookMessageDelivererService implements IWebHookMessageDelivererService
{


    /**
     * Envia uma mensagem para o WebHook passado por parâmetro.
     *
     * @param weebHookUrl A URL do WebHook.
     * @param mensagem A mensagem a ser enviada para o WebHoook.
     * @return 'true' caso a mensagem seja enviada com sucesso, 'false' caso não.
     */
    @Override
    public boolean enviarMensagem(String weebHookUrl, String mensagem)
    {

        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(weebHookUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mensagem, StandardCharsets.UTF_8))
                .build();

        try{

            HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204) {
                log.info("Mensagem enviada com sucesso!");
                return true;
            } else {

                log.error("Mensagem não enviada");
                return false;
            }
        } catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }

    }
}
