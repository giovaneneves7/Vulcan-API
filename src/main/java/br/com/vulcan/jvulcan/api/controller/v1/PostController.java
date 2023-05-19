package br.com.vulcan.jvulcan.api.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class PostController
{

    @GetMapping("/posts/recentes")
    public void pegarPostsRecentes()
    {



    }

}
