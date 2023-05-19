package br.com.vulcan.jvulcan.api.entity.post.service;

import br.com.vulcan.jvulcan.api.entity.post.model.Post;

public interface IPostService
{
    void notificarNovaPostagem(Post post);
}
