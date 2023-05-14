package br.com.vulcan.jvulcan.api.novel.service;

import br.com.vulcan.jvulcan.api.novel.model.Novel;
import br.com.vulcan.jvulcan.api.novel.repository.NovelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovelService implements INovelService
{

    @Autowired
    NovelRepository novelRepository;

    /**
     * Retorna uma lista com todas as novels.
     * @return uma lista com todas as novels.
     */
    @Override
    public List<Novel> listarTodas()
    {
        return this.novelRepository.findAll();
    }

    /**
     * Retorna uma lista com todas as novels com a nacionalidade especificada.
     * @return uma lista com todas as novels com a nacionalidade especificada.
     */
    @Override
    public List<Novel> listarTodas(String nacionalidade)
    {

        return this.novelRepository.findByNacionalidade(nacionalidade);

    }

    /**
     * Salva uma novel na base de dados.
     * @param novel A novel que será salva.
     * @return 'true' caso a novel seja salva, 'false' caso contrário.
     */
    @Override
    public boolean salvar(Novel novel)
    {
        List<Novel> novels = listarTodas();

        boolean slugExiste = novels.stream()
                                      .anyMatch(n -> n.getSlug().equals(novel.getSlug()));
        boolean indiceExiste = novels.stream()
                .anyMatch(n -> n.getSlug().equals(novel.getIndice()));


        //--+ Define o rank da novel +--//
        if(!slugExiste && !indiceExiste)
        {
            novels.add(novel);

            novels.sort((n1, n2) -> Integer.compare(n2.getViewsTotais(), n1.getViewsTotais()));

            for(int i = 0; i < novels.size(); i++)
            {
                novels.get(i).setColocacao((i + 1));
            }
            this.novelRepository.saveAll(novels);
            return true;
        }

        return false;
    }
}
