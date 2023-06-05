package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;

import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;
import br.com.vulcan.jvulcan.api.infrastructure.service.Facade;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/nekoyasha7/jvulcan-api/v1")
public class ChibataController
{

    private final String NOVEL_NOT_FOUND = "A novel requisitada não existe na base de dados";

    @Value("${api_key}")
    private String API_KEY;

    @PostMapping
    public void init()
    {
        log.info("Endpoint usando a chave de API: ".concat(API_KEY));
    }

    @Autowired
    Facade facade;

    @PostMapping(path = "/olho-da-chibata/membros/membro")
    public ResponseEntity<?> cadastrar(@RequestBody OlhoDaChibata dadosChibata,
                                       @RequestHeader("Api-Key") String chaveAPI) throws ObjectNotFoundException
    {

        if(!chaveAPI.equals(API_KEY))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não tem permissão para acessar este recurso, bleh!");
        }

        if(!this.facade.cadastrarDadosChibata(dadosChibata))
        {
            throw new ObjectNotFoundException(NOVEL_NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Dados registrados com sucesso!");

    }

    @GetMapping("/olho-da-chibata/membros")
    public ResponseEntity<?> listarTodos(@RequestHeader("Api-Key") String chaveAPI)
    {

        if(!chaveAPI.equals(API_KEY))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não tem permissão para acessar este recurso, bleh!");
        }

        return (facade.listarOlhoDaChibata().isEmpty())
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ops, nada a ser exibido aqui 👀")
                : ResponseEntity.ok(this.facade.listarOlhoDaChibata());

    }

    @PutMapping("/olho-da-chibata/membros/membro")
    public ResponseEntity<?> atualizar(@RequestBody OlhoDaChibata dadosChibata,
                                       @RequestHeader("Api-Key") String chaveAPI)
    {
        if(!chaveAPI.equals(API_KEY))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não tem permissão para acessar este recurso, bleh!");
        }

        if(!this.facade.atualizarDadosChibata(dadosChibata))
        {
            throw new ObjectNotFoundException("Impossível atualizar um registro que não existe na base de dados.");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dados atualizados com sucesso!");

    }

    @PostMapping("/olho-da-chibata/membros/cobranca")
    public ResponseEntity<?> cobrarMembros(@RequestHeader("Api-Key") String chaveAPI)
    {
        if(!chaveAPI.equals(API_KEY))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não tem permissão para acessar este recurso, bleh!");
        }

        return (this.facade.descerAChibata())
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body("Cobrança ralizada com sucesso!")
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body("Lista de cobrança vazia!");
    }

}
