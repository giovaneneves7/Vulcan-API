package br.com.vulcan.jvulcan.api.entity.view.service;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;
import br.com.vulcan.jvulcan.api.entity.view.model.View;
import br.com.vulcan.jvulcan.api.entity.view.model.dto.request.IncrementarViewsDTO;
import br.com.vulcan.jvulcan.api.entity.view.model.dto.response.IncrementarViewsDto;
import br.com.vulcan.jvulcan.api.entity.view.repository.NovelViewsRepository;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NovelViewsService implements INovelViewsService{

    private final String NOVEL_NOT_FOUND = "A novel requisita não existe na base de dados";
    @Autowired
    NovelViewsRepository novelViewsRepository;

    @Autowired
    NovelRepository novelRepository;

    /**
     * Incrementa as views totais de uma novel.
     *
     * @param viewsDTO O DTO com dados da novel que terá as views incrementadas.
     *
     * @return Um DTO com os dados atualizados da novel.
     */
    @Override
    public IncrementarViewsDto incrementarViews(IncrementarViewsDTO viewsDTO) {

        Optional<View> optionalDadosViews = novelViewsRepository.findByCategoria(viewsDTO.categoria());

        if(!optionalDadosViews.isPresent()){

            Optional<Novel> optionalNovel = novelRepository.findByNome(viewsDTO.categoria());

            if(!optionalNovel.isPresent()){

                throw new ObjectNotFoundException(NOVEL_NOT_FOUND);
            }

            Novel novel = optionalNovel.get();
            novelViewsRepository.save(new View(novel.getNome(), novel.getViewsTotais(), novel.getViewsMensais()));

        } else{

            View view = optionalDadosViews.get();
            view.setViewsTotais((view.getViewsTotais() + 1));

            novelViewsRepository.save(view);

        }

        return new IncrementarViewsDto(optionalDadosViews.get().getCategoria(), optionalDadosViews.get().getViewsTotais());


    }
}
