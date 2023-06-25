package br.com.vulcan.jvulcan.api.infrastructure.service.discord;

import br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds.MensagemJson;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IWebHookMessageDelivererService
{

    /**
     * Envia uma mensagem para o WebHook passado por parâmetro.
     *
     * @param weebHookUrl A URL do WebHook.
     * @param mensagem A mensagem a ser enviada para o WebHoook.
     * @return 'true' caso a mensagem seja enviada com sucesso, 'false' caso não.
     */
    boolean enviarMensagem(String weebHookUrl, MensagemJson mensagem);
}
