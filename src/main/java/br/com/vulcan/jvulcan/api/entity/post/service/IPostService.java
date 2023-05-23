package br.com.vulcan.jvulcan.api.entity.post.service;

import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import br.com.vulcan.jvulcan.api.infrastructure.exception.NovelNotFoundException;

public interface IPostService
{

    /**
     * Envia uma embed via Webhook com informações de uma nova postagem no site.
     * @param post O post que será notificado via Webhook.
     */
    void notificarNovaPostagem(Post post) throws NovelNotFoundException;
}
