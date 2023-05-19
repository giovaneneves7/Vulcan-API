package br.com.vulcan.jvulcan.api.entity.banners.service;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import br.com.vulcan.jvulcan.api.entity.banners.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BannerService implements IBannerService
{

    @Autowired
    BannerRepository bannerRepository;


    /**
     * Adiciona um banner no banco de dados.
     * @param banner O Banner que será cadastrado.
     * @return 'True' caso o banner seja cadastrado, 'false' caso contrário.
     */
    @Override
    public boolean salvar(Banner banner) {

        Optional<Banner> optionalBanner = bannerRepository.findBannerByNome(banner.getNome());

        if(optionalBanner.isPresent())
        {
            return false;
        }

        bannerRepository.save(banner);
        return true;
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
