package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.entity.servidores.model.dto.CadastrarServidorAutorDto;
import br.com.vulcan.jvulcan.api.entity.servidores.service.IServidorAutorService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class ServidorAutorController {

    @Autowired
    IServidorAutorService servidorAutorService;


    private final String API_KEY;

    public ServidorAutorController(@Value("${api_key}") String apiKey)
    {
        API_KEY = apiKey;
    }

    private HashMap<String, String> erros;

    @PostConstruct
    public void init()
    {
        if(!API_KEY.isEmpty())
            log.debug("Endpoint usando API");

    }

    @PostMapping("/servidores-autores/servidor")
    ResponseEntity<?> cadastrarServidorAutor(@RequestHeader(name = "Api-Key") String chaveApi,
                                             @RequestBody @Valid CadastrarServidorAutorDto servidorAutorDto,
                                             BindingResult result) {
        erros = new HashMap<>();

        if(!chaveApi.equals(API_KEY))
        {
            log.error("Acesso negado ao endpoint, chave de API incorretada.");
            erros.put("api_permission_error", "Você não tem autorização para acessar este endpoint, bleh!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(erros);
        }

        if(result.hasErrors())
        {
            for(FieldError campo : result.getFieldErrors())
            {
                erros.put(campo.getField(), campo.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(erros);
        }


        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(servidorAutorService.cadastrarServidorAutor(servidorAutorDto));

    }
}
