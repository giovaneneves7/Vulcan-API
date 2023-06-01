package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.entity.post.model.Post;
import br.com.vulcan.jvulcan.api.infrastructure.exception.MessageNotSentException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;
import br.com.vulcan.jvulcan.api.infrastructure.service.IFacade;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Slf4j
@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class PostController {

    @Value("${api_key}")
    private String API_KEY;

    @PostConstruct
    public void init() {
        if (!API_KEY.isEmpty())
            log.info("Enpoint usando api");
    }

    @Autowired
    IFacade facade;

    /**
     * Notifica as novas postagens via WebHook (Discord).
     * 
     * @param post     O post que será notificado via WebHoook.
     * @param ChaveApi A chave da api.
     * @return OK - O post foi notificado com sucesso;
     *         UNAUTHORIZED - Sem permissão para acessar a API;
     *         NOT_FOUND - A categoria a ser notificada não existe na base de dados;
     *         BAD_REQUEST - A mensagem webhook não pôde ser enviada.
     */
    @PostMapping("/posts/post")
    public ResponseEntity<?> pegarPostsRecentes(@RequestBody Post post,
            @RequestHeader(name = "api_key") String chaveAPI) {

        // --+ Verifica se a chave de API é válida +--//
        if (!API_KEY.equals(chaveAPI)) {

            log.info("Chave de API inválida");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Você não tem permissão para acessar esse recurso, bleh!");

        }

        try {

            this.facade.notificarNovaPostagem(post);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (ObjectNotFoundException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());

        } catch (MessageNotSentException ex) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }

    }

}
