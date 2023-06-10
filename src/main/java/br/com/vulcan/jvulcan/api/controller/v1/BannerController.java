package br.com.vulcan.jvulcan.api.controller.v1;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import br.com.vulcan.jvulcan.api.entity.banners.model.dto.BannerDto;
import br.com.vulcan.jvulcan.api.entity.banners.model.dto.CadastrarBannerDto;
import br.com.vulcan.jvulcan.api.infrastructure.service.IFacade;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

            List<BannerDto> bannersRetornados = BannerDto.converterLista(bannersAleatorios);

            return ResponseEntity.status(HttpStatus.OK).body(bannersRetornados);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(banners.stream().map(BannerDto::new)
                                      .collect(Collectors.toList()));
    }

    @GetMapping(path = "/banners/banner")
    public ResponseEntity<?> pegarBannerAleatorio(@RequestHeader("Api-Key") String chaveApi)
    {

        if(!chaveApi.equals(API_KEY))
        {
            if(!erros.containsKey("api_permission_error"))
                erros.put("api_permission_erros", "Você não tem permissão para acessar este endpoint, bleh!");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erros);
        }

        return ResponseEntity.status(HttpStatus.OK).body(facade.pegarBannerAleatorio());

    }

    @PostMapping(path = "banners/banner")
    public ResponseEntity<?> salvarBanner(@RequestBody CadastrarBannerDto bannerDto,
                                          @RequestHeader(name = "Api-Key") String chaveApi)
    {

        log.info(bannerDto.toString());

        if(!chaveApi.equals(API_KEY))
        {
            if(!erros.containsKey("api_permission_error"))
                erros.put("api_permission_erros", "Você não tem permissão para acessar este endpoint, bleh!");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erros);
        }

        this.facade.salvarBanner(bannerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Bannner cadastrado com sucesso.");

    }

    @DeleteMapping(path = "/banners/banner/{id}")
    public ResponseEntity<?> deletarBanner(@PathVariable(name = "id") Long id,
                                           @RequestHeader(name = "Api-Key") String chaveApi)
    {

        if(!chaveApi.equals(API_KEY))
        {
            if(!erros.containsKey("api_permission_error"))
                erros.put("api_permission_erros", "Você não tem permissão para acessar este endpoint, bleh!");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                          .body(erros);
        }

        return ResponseEntity.status(HttpStatus.OK)
                             .body(this.facade.deletarBannerPorId(id));

    }

}
