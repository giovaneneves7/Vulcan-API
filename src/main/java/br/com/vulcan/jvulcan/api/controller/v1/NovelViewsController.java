package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.infrastructure.service.Facade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping(path = "/nekoyasha7/jvulcan-api/v1")
public class NovelViewsController {

    @Value("${api_key}")
    private String apiKey;

    @Autowired
    Facade facade;

    private HashMap<String, String> erros;

    @PutMapping(path = "novels/novel/{categoria}/views/view")
    public ResponseEntity<?> incrementarViews(@RequestHeader(name = "Api-Key") String chaveApi,
                                              @PathVariable(name = "categoria") String novel,
                                              BindingResult result){

        erros = new HashMap<>();

        if(!chaveApi.equals(apiKey)){

            erros.put("api_permissiom_error", "Você não tem permissão para acessar este endpoint, bleh!");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erros);
        }

        if(result.hasErrors()){

            for(FieldError erro : result.getFieldErrors()){

                erros.put(erro.getField(), erro.getDefaultMessage());

            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
        }

        return ResponseEntity.status(HttpStatus.OK).body("heyyyyy");
    }
}
