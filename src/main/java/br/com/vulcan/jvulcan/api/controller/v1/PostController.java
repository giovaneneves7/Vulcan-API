package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import br.com.vulcan.jvulcan.api.infrastructure.exception.MessageNotSentException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;
import br.com.vulcan.jvulcan.api.infrastructure.service.IFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class PostController
{

    @Autowired
    IFacade facade;

    /**
     * Notifica as novas postagens via WebHook (Discord).
     * @param post O post que será notificado via WebHoook.
     */
    @PostMapping("/posts/post")
    public ResponseEntity<?> pegarPostsRecentes(@RequestBody Post post)
    {

        try
        {

            this.facade.notificarNovaPostagem(post);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (ObjectNotFoundException ex)
        {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(ex.getMessage());

        } catch (MessageNotSentException ex)
        {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(ex.getMessage());
        }

    }

}
