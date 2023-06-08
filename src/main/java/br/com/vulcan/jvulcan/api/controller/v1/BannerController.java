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

import java.util.HashMap;

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

    @GetMapping(path = "/banners/banner")
    public ResponseEntity<Banner> pegarBannerAleatorio()
    {

        return ResponseEntity.ok(facade.pegarBannerAleatorio());

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

        if(!this.facade.salvarBanner(banner))
        {
            return ResponseEntity.badRequest().body("Já existe um banner com este nome!");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Bannner cadastrado com sucesso.");

    }
}
