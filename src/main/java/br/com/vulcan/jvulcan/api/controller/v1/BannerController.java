package br.com.vulcan.jvulcan.api.controller;

import br.com.vulcan.jvulcan.api.banners.model.Banner;
import br.com.vulcan.jvulcan.api.infrastructure.service.IFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "nekoyasha7/jvulcan-api/v1")
public class BannerController
{

    @Autowired
    IFacade facade;

    @GetMapping(path = "/banners/banner")
    public ResponseEntity<Banner> pegarBannerAleatorio()
    {

        return ResponseEntity.ok(facade.pegarBannerAleatorio());

    }

    @PostMapping(path = "banners/banner")
    public ResponseEntity<String> salvarBanner(@RequestBody Banner banner)
    {

        if(!this.facade.salvarBanner(banner))
        {
            return ResponseEntity.badRequest().body("Já existe um banner com este nome!");
        }

        return ResponseEntity.ok("Banner cadastrado com successo!");

    }
}
