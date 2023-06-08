package br.com.vulcan.jvulcan.api.entity.banners.service;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import br.com.vulcan.jvulcan.api.entity.banners.repository.BannerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class BannerService implements IBannerService
{

    @Autowired
    BannerRepository bannerRepository;


    /**
     * Adiciona um banner no banco de dados.
     * @param banner O Banner que será cadastrado.
     */
    @Override
    public void cadastrarBanner(Banner banner) {

        boolean nomeExiste = bannerRepository.findAll().stream()
                                                        .map(Banner::getNome)
                                                        .anyMatch(nomeBanner -> nomeBanner.equals(banner.getNome()));

        if(nomeExiste)
        {
            log.info("Já existe um banner com este nome na base de dados!");
            return;
        }

        bannerRepository.save(banner);

    }

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
