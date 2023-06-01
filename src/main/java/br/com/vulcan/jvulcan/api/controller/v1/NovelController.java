package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.entity.cargo.model.Cargo;
import br.com.vulcan.jvulcan.api.infrastructure.service.IFacade;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class NovelController
{

    @Value("${api_key}")
    private String API_KEY;

    @PostConstruct
    public void init()
    {
        if(!API_KEY.isEmpty())
            log.info("Enpoint usando api");
    }


    @Autowired
    IFacade facade;

    /**
     * Retorna uma lista com todas as novels na base de dados.
     * @param nacionalidade (Opicional) A nacionalidade das novels que serão retornadas.
     * @return Lista de novels da base de dados segundo os parametros passados.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/novels")
    public ResponseEntity<List<Novel>> listarNovels(@RequestParam(name = "nacionalidade", required = false) String nacionalidade,
                                                    @RequestParam(name = "tipo", required = false) String tipo)
    {

        //--+ Retorna lista com nacionalidade especificada +--//
        if(nacionalidade != null)
        {
            return ResponseEntity.ok(this.facade.listarTodasNovels(nacionalidade));
        }

        //--+ Retorna lista com tipo passado por parametro+--//
        if(tipo != null)
        {
            List<Novel> novelsRetornadas = new ArrayList<>();

            switch (tipo)
            {
                case "traducoes":

                    novelsRetornadas.addAll(this.facade.listarTodasNovels("oci"));
                    novelsRetornadas.addAll(this.facade.listarTodasNovels("ch"));
                    novelsRetornadas.addAll(this.facade.listarTodasNovels("co"));
                    novelsRetornadas.addAll(this.facade.listarTodasNovels("jp"));

                    if(!novelsRetornadas.isEmpty())
                        return ResponseEntity.ok(novelsRetornadas);

                case "originais":

                    novelsRetornadas.addAll(this.facade.listarTodasNovels("br"));

                    if(!novelsRetornadas.isEmpty())
                        return ResponseEntity.ok(novelsRetornadas);

            }
        }

        return ResponseEntity.ok(this.facade.listarTodasNovels());

    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("novels/novel")
    public ResponseEntity<List<Novel>> atualizarCargo(@RequestBody List<Cargo> cargos)
    {

        return ResponseEntity.ok(this.facade.atualizarCargoDasNovels(cargos));

    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/novels/novel")
    public ResponseEntity<String> cadastrarNovel(@RequestBody Novel novel){

        if(this.facade.salvarNovel(novel))
        {
            return ResponseEntity.ok("Novel salva com sucesso!");

        } else {

            return ResponseEntity.badRequest().body("Houve um erro ao tentar salvar a novel na base de dados");

        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/novels/novel/{slug}")
    public ResponseEntity<Novel> buscarPorSlug(@PathVariable(name = "slug") String slug)
    {

        return ResponseEntity.ok(facade.buscarNovelPorSlug(slug));

    }
}
