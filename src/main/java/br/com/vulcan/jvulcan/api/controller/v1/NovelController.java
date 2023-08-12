package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarNovelDto;
import br.com.vulcan.jvulcan.api.infrastructure.enums.Errors;
import br.com.vulcan.jvulcan.api.infrastructure.service.IFacade;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class NovelController
{

    @Value("${api_key}")
    private String API_KEY;

    private HashMap<String, String> erros;

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

    @GetMapping(path = "/novels/novel/{slug}")
    public ResponseEntity<?> pegarNovelPorSlug(@PathVariable(name = "slug") String slug,
                                               @RequestHeader(name = "Api-Key") String chaveApi,
                                               @RequestParam(name = "filter") Optional<String> filtro){

        log.info("Requisitando slug {} com filtro {}", slug, filtro);
        return ResponseEntity.status(HttpStatus.OK).body(facade.buscarNovelPorSlug(slug, filtro));

    }

    /*@CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("novels/novel")
    public ResponseEntity<List<Novel>> atualizarCargo(@RequestBody List<Cargo> cargos)
    {

        return ResponseEntity.ok(this.facade.atualizarCargoDasNovels(cargos));

    }*/
    @CrossOrigin(origins = {"http://localhost:3000", "https://apill.vulcannovel.com.br", "https://vulcannovel.com.br"}, allowedHeaders = "Content-Type")
    @PostMapping("/novels/novel")
    public ResponseEntity<?> cadastrarNovel(@RequestBody CadastrarNovelDto novelDto,
                                            BindingResult result,
                                            @RequestHeader(name = "API-KEY") String chaveApi){

        erros = new HashMap<>();

        if(!chaveApi.equals(API_KEY)){

            log.error("Acesso negado ao endpoint de cadastro!");
            erros.put(Errors.API_PERMISSION_ERROR.getKey(), Errors.API_PERMISSION_ERROR.getErro());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erros);
        }

        if(result.hasErrors()){

            for(FieldError erro : result.getFieldErrors()){
                erros.put(erro.getField(), erro.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.facade.salvarNovel(novelDto));

    }
}
