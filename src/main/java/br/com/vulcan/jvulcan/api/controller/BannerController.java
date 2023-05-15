package br.com.vulcan.jvulcan.api.controller;

import br.com.vulcan.jvulcan.api.banners.model.Banner;
import br.com.vulcan.jvulcan.api.infrastructure.service.IFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
