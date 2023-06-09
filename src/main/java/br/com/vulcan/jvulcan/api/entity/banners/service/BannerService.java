package br.com.vulcan.jvulcan.api.entity.banners.service;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import br.com.vulcan.jvulcan.api.entity.banners.repository.BannerRepository;
import br.com.vulcan.jvulcan.api.infrastructure.exception.EmptyListException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectAlreadyExistsException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;
import br.com.vulcan.jvulcan.api.infrastructure.origin.OrigensAutorizadas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class BannerService implements IBannerService
{

    private final String BANNER_NOT_FOUND = "O banner requisitado não existe na base de dados";
    private final String ALREADY_EXISTS_BANNER = "Já existe um banner com este nome na base de dados";
    private final String EMPTY_BANNER_LIST = "A lista de banners está vazia";

    @Autowired
    BannerRepository bannerRepository;

    /**
     * Deleta o banner que tiver o ID passado por parâmetro.
     *
     * @param id O ID do banner que será deletado.
     * @return as informações do banner que foi deletado.
     */
    @Override
    public Banner deletarBannerPorId(Long id)
    {
        return this.bannerRepository.findById(id)
                                    .filter(banner -> {
                                        this.bannerRepository.delete(banner);
                                        return true;
                                    })
                                    .orElseThrow(ObjectNotFoundException::new);

    }

    /**
     * Adiciona um banner no banco de dados.
     *
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
            throw new ObjectAlreadyExistsException(ALREADY_EXISTS_BANNER);
        }

        bannerRepository.save(banner);

    }

    /**
     * Retorna um banner aleatório da base de dados.
     *
     * @return um banner aleatório.
     */
    @Override
    public Banner pegarBannerAleatorio()
    {

        List<Banner> banners = bannerRepository.findAll();

        if(banners.isEmpty())
            throw new EmptyListException(EMPTY_BANNER_LIST);

        Random rand = new Random();
        int indiceAleatorio = rand.nextInt(banners.size());

        return banners.get(indiceAleatorio);


    }

    /**
     * Lista todos os banners da base de dados.
     *
     * @return lista com todos os banners cadastrados na base de dados, ou nulo, caso não haja registros.
     */
    @Override
    public List<Banner> listarTodosBanners()
    {
        List<Banner> banners = bannerRepository.findAll();

        if(banners.isEmpty())
            throw new EmptyListException(EMPTY_BANNER_LIST);

        return banners;
    }
}
