package br.com.vulcan.jvulcan.api.entity.banners.service;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import br.com.vulcan.jvulcan.api.entity.banners.model.dto.CadastrarBannerDto;
import br.com.vulcan.jvulcan.api.entity.banners.repository.BannerRepository;
import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;
import br.com.vulcan.jvulcan.api.infrastructure.exception.EmptyListException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectAlreadyExistsException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;

import jakarta.transaction.Transactional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class BannerService implements IBannerService {

    private final String NOVEL_NOT_FOUND = "A model associada a este banner não existe na base de dados";
    private final String BANNER_NOT_FOUND = "O banner requisitado não existe na base de dados";
    private final String ALREADY_EXISTS_BANNER = "Já existe um banner com este nome ou link na base de dados";
    private final String EMPTY_BANNER_LIST = "A lista de banners está vazia";

    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    NovelRepository novelRepository;

    /**
     * Deleta o banner que tiver o ID passado por parâmetro.
     *
     * @param id O ID do banner que será deletado.
     * @return as informações do banner que foi deletado.
     */
    @Override
    public Banner deletarBannerPorId(Long id) {
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
     * @param bannerDto - Os dados do Banner que será cadastrado.
     */
    @Override
    @Transactional
    public void cadastrarBanner(CadastrarBannerDto bannerDto) {

        boolean nomeExiste = bannerRepository.findAll().stream()
                .map(Banner::getNome)
                .anyMatch(nomeBanner -> nomeBanner.equals(bannerDto.getNome()));

        boolean linkExiste = bannerRepository.findAll().stream()
                .map(Banner::getLink)
                .anyMatch(linkBanner -> linkBanner.equals(bannerDto.getLink()));

        if (nomeExiste || linkExiste) {
            throw new ObjectAlreadyExistsException(ALREADY_EXISTS_BANNER);
        }

        //--+ Salva o banner convertido na base de dados. +--//
        Banner banner;
        banner = converterParaBanner(bannerDto);
        bannerRepository.save(banner);

    }

    /**
     * Retorna um banner aleatório da base de dados.
     *
     * @return um banner aleatório.
     */
    @Override
    public Banner pegarBannerAleatorio() {

        List<Banner> banners = bannerRepository.findAll();

        if (banners.isEmpty())
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
    public List<Banner> listarTodosBanners() {
        List<Banner> banners = bannerRepository.findAll();

        if (banners.isEmpty())
            throw new EmptyListException(EMPTY_BANNER_LIST);

        return banners;
    }

    private Banner converterParaBanner(CadastrarBannerDto bannerDto) {

        Banner banner = new Banner();
        banner.setNome(bannerDto.getNome());
        banner.setLink(bannerDto.getLink());
        banner.setUrlRedirecionamento(bannerDto.getUrlRedirecionamento());

        return banner;
    }
}
