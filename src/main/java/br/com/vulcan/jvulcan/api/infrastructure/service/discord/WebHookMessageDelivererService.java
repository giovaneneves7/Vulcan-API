package br.com.vulcan.jvulcan.api.infrastructure.service.discord;

import br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds.MensagemJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
    public boolean enviarMensagem(String weebHookUrl, MensagemJson mensagem) {

        String mensagemJson = "";

        try{

            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            mensagemJson = objectMapper.writeValueAsString(mensagem);

        } catch(JsonProcessingException ex){
            log.error("Ocorreu um erro ao serializar a mensagem");
            ex.printStackTrace();
        }

        if(mensagemJson.isEmpty()) return false;

        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(weebHookUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mensagemJson, StandardCharsets.UTF_8))
                .build();

        try{

            HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.toString());

            if (response.statusCode() == 204) {

                log.info("Mensagem enviada com sucesso!");
                return true;

            } else {

                log.error("Mensagem não enviada");
                return false;
            }
        } catch(Exception ex)
        {
            log.info("Erro ao tentar enviar a mensagem");
            ex.printStackTrace();
            return false;
        }

    }
}
