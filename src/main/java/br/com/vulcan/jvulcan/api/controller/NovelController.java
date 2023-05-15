package br.com.vulcan.jvulcan.api.controller;

import br.com.vulcan.jvulcan.api.infrastructure.service.IFacade;
import br.com.vulcan.jvulcan.api.novel.model.Novel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class NovelController
{

    @Autowired
    IFacade facade;

    /**
     * Retorna uma lista com todas as novels na base de dados.
     * @param nacionalidade (Opicional) A nacionalidade das novels que serão retornadas.
     * @return Lista de novels da base de dados.
     */
    @GetMapping(path = "/novels")
    public ResponseEntity<List<Novel>> listarTodas(@RequestParam(name = "nacionalidade", required = false) String nacionalidade)
    {

        if(nacionalidade != null)
        {
            return ResponseEntity.ok(this.facade.listarTodasNovels(nacionalidade));
        }

        return ResponseEntity.ok(this.facade.listarTodasNovels());

    }

    @PostMapping("/novels/novel")
    public ResponseEntity<String> cadastrarNovel(@RequestBody Novel novel){

        if(this.facade.salvarNovel(novel))
        {
            return ResponseEntity.ok("Novel salva com sucesso!");

        } else {

            return ResponseEntity.badRequest().body("Houve um erro ao tentar salvar a novel na base de dados");

        }
    }
}
