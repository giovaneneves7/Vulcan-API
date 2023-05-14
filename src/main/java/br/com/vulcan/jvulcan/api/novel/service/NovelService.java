package br.com.vulcan.jvulcan.api.novel.service;

import br.com.vulcan.jvulcan.api.novel.model.Novel;
import br.com.vulcan.jvulcan.api.novel.repository.NovelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        //--+ Verifica se há registro no banco de dados +--//
        if(novels.isEmpty())
        {
            novel.setColocacao(1);
            novel.setColocacaoMensal(1);
            novelRepository.save(novel);

            return true;
        }

        boolean slugExiste = novels.stream()
                                      .anyMatch(n -> n.getSlug().equals(novel.getSlug()));
        boolean indiceExiste = novels.stream()
                .anyMatch(n -> n.getSlug().equals(novel.getIndice()));


        //--+ Organiza a novel de acordo com as views +--//
        if(!slugExiste && !indiceExiste)
        {
            novels.add(novel);

            reorganizarPorViewsMensais(novels);
            reorganizarPorViewsTotais(novels);

            for(Novel nvl : novels)
            {
                Optional<Novel> existeNoBanco = novelRepository.findById((long) nvl.getColocacao());

                if(existeNoBanco.isPresent())
                {
                    Novel novelAtualizada = existeNoBanco.get();
                    novelAtualizada.setColocacaoMensal(nvl.getColocacaoMensal());
                    novelAtualizada.setViewsTotais(nvl.getViewsTotais());
                    novelAtualizada.setViewsMensais(nvl.getViewsMensais());
                    novelAtualizada.setSlug(nvl.getSlug());
                    novelAtualizada.setNome(nvl.getNome());
                    novelAtualizada.setNacionalidade(nvl.getNacionalidade());
                    novelAtualizada.setCapa(nvl.getCapa());
                    novelAtualizada.setIndice(nvl.getIndice());
                    novelAtualizada.setAutor(nvl.getAutor());
                    novelAtualizada.setQuantidadeCapitulos(nvl.getQuantidadeCapitulos());
                    novelAtualizada.setCargo(nvl.getCargo());
                    novelAtualizada.setGeneros(nvl.getGeneros());
                    novelAtualizada.setEscritor(nvl.getEscritor());
                    novelAtualizada.setEstrelas(nvl.getEstrelas());
                    novelAtualizada.setDataCriacaoIndice(nvl.getDataCriacaoIndice());
                    novelAtualizada.setSinopse(nvl.getSinopse());
                    this.novelRepository.save(novelAtualizada);
                } else{
                    this.novelRepository.save(nvl);
                }
            }

            return true;
        }


        return false;
    }

    private void reorganizarPorViewsMensais(List<Novel> novels)
    {
        for(int i = 0; i < novels.size(); i++)
        {
            novels.get(i).setColocacaoMensal(i + 1);
        }
    }

    private void reorganizarPorViewsTotais(List<Novel> novels)
    {
        novels.sort((n1, n2) -> Integer.compare(n2.getViewsTotais(), n1.getViewsTotais()));

        for(int i = 0; i < novels.size(); i++)
        {
            novels.get(i).setColocacao((i + 1));
        }
    }
}
