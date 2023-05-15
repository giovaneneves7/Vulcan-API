package br.com.vulcan.jvulcan.api.banners.service;

import br.com.vulcan.jvulcan.api.banners.model.Banner;
import br.com.vulcan.jvulcan.api.banners.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class BannerService implements IBannerService
{

    @Autowired
    BannerRepository bannerRepository;


    /**
     * Retorna um banner aleatório da base de dados.
     * @return um banner aleatório.
     */
    @Override
    public Banner pegarBannerAleatorio()
    {

        List<Banner> banners = bannerRepository.findAll();
        Random rand = new Random();
        int indiceAleatorio = rand.nextInt(banners.size());

        return banners.get(indiceAleatorio);


    }
}
