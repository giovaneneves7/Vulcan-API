package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;

import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;
import br.com.vulcan.jvulcan.api.infrastructure.service.Facade;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/nekoyasha7/jvulcan-api/v1")
public class ChibataController
{

    private final String NOVEL_NOT_FOUND = "A novel requisitada não existe na base de dados";

    @Autowired
    Facade facade;

    @PostMapping(path = "/olho-da-chibata")
    public ResponseEntity<?> cadastrar(@RequestBody OlhoDaChibata dadosChibata) throws ObjectNotFoundException
    {

        if(!this.facade.cadastrarDadosChibata(dadosChibata))
        {
            throw new ObjectNotFoundException(NOVEL_NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Dados registrados com sucesso!");

    }
}
