package br.com.vulcan.jvulcan.api.controller.v1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class StatusController {

    @Value("${api_key}")
    private String API_KEY;

    /**
     * @param api_key A chave da API.
     * @return 200 caso a conexão seja realizada com sucesso, 401 caso contrário.
     */
    @GetMapping(path = "/status")
    public ResponseEntity<?> getStatus(@RequestHeader(name = "api_key") String api_key) {

        if (!api_key.equals(API_KEY)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Você não tem permissão para acessar esse recurso, bleh!");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("Conexão realizada com sucesso!");
    }

}
