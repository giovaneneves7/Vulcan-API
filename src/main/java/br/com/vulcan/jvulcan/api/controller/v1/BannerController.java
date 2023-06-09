package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import br.com.vulcan.jvulcan.api.infrastructure.service.IFacade;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class BannerController
{

    private final HashMap<String, String> erros = new HashMap<>();

    @Value("${api_key}")
    private String API_KEY;

    @Autowired
    IFacade facade;

    @PostConstruct
    public void init()
    {
        if(!API_KEY.isEmpty())
            log.warn("Endpoint privado");
    }

    @GetMapping(path = "/banners")
    public ResponseEntity<?> listarTodosBanners(@RequestParam(name = "max", required = false) Integer max,
                                                @RequestHeader("Api-Key") String chaveApi){

        if(!chaveApi.equals(API_KEY))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não tem permissão para acessar este endoint, bleh!");

        log.info("Listando todas os banners");
        List<Banner> banners = new ArrayList<>(this.facade.listarTodosBanners());

        if(max != null)
        {
            if(banners.size() < max)
            {
                if(!erros.containsKey("out_of_bound_error"))
                    erros.put("out_of_bound_error", "Número de banners insuficiente");

                return ResponseEntity.status(HttpStatus.CONFLICT).body(erros);
            }

            List<Banner> bannersAleatorios = IntStream.range(0 , max)
                    .mapToObj(banner -> banners.get(new Random().nextInt(banners.size())))
                    .distinct()
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(bannersAleatorios);
        }

        return ResponseEntity.status(HttpStatus.OK).body(banners);
    }

    @GetMapping(path = "/banners/banner")
    public ResponseEntity<?> pegarBannerAleatorio(@RequestHeader("Api-Key") String chaveApi)
    {

        if(!chaveApi.equals(API_KEY))
        {
            if(!erros.containsKey("api_permission_error"))
                erros.put("api_permission_erros", "Você não tem permissão para acessar este endpoint, bleh!");

            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erros);
        }

        return ResponseEntity.status(HttpStatus.OK).body(facade.pegarBannerAleatorio());

    }

    @PostMapping(path = "banners/banner")
    public ResponseEntity<?> salvarBanner(@RequestBody Banner banner,
                                          @RequestHeader(name = "Api-Key") String chaveApi)
    {

        if(!chaveApi.equals(API_KEY))
        {
            if(!erros.containsKey("api_permission_error"))
                erros.put("api_permission_erros", "Você não tem permissão para acessar este endpoint, bleh!");

            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erros);
        }

        this.facade.salvarBanner(banner);
        return ResponseEntity.status(HttpStatus.CREATED).body("Bannner cadastrado com sucesso.");

    }

    @DeleteMapping(path = "/banners/banner/{id}")
    public ResponseEntity<?> deletarBanner(@PathVariable(name = "id") Long id,
                                           @RequestHeader(name = "Api-Keey") String chaveApi)
    {

        if(!chaveApi.equals(API_KEY))
        {
            if(!erros.containsKey("api_permission_error"))
                erros.put("api_permission_erros", "Você não tem permissão para acessar este endpoint, bleh!");

            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erros);
        }



    }
}
