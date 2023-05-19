package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import br.com.vulcan.jvulcan.api.infrastructure.service.IFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class PostController
{

    @Autowired
    IFacade facade;

    @PostMapping("/posts/post")
    public void pegarPostsRecentes(@RequestBody Post post)
    {

        this.facade.notificarNovaPostagem(post);

    }

}
