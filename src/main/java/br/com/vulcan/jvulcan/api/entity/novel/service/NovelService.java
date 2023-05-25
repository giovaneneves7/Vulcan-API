package br.com.vulcan.jvulcan.api.entity.novel.service;

import br.com.vulcan.jvulcan.api.entity.cargo.model.Cargo;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;

import jakarta.transaction.Transactional;
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
    @Transactional
    public boolean salvar(Novel novel)
    {
        List<Novel> novels = this.novelRepository.findAll();

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

            //--+ Reordena a lista +--//
            reorganizarPorViewsMensais(novels);
            reorganizarPorViewsTotais(novels);

            this.novelRepository.saveAll(novels);



            return true;
        }


        return false;
    }

    @Override
    public void deletar(long id)
    {

        this.novelRepository.deleteById(id);

    }

    /**
     * Busca uma novel pelo slug passado.
     * @param slug O slug da novel.
     * @return A novel com o slug passado por parâmetro, 'null' caso ela não exista.
     */
    @Override
    public Novel buscarPorSlug(String slug) {

        Optional<Novel> optionalNovel = this.novelRepository.findBySlug(slug);

        if(optionalNovel.isPresent())
        {
            return optionalNovel.get();
        }

        return null;

    }

    /**
     * Atualiza o cargo das novels.
     * @param cargos A lista com cargos do servidor do discord da Vulcan.
     * @return Lista com as novels na base de dados.
     */
    @Override
    public List<Novel> atualizarCargo(List<Cargo> cargos)
    {
        for(Cargo cargo : cargos)
        {

            Optional<Novel> optionalNovel = this.novelRepository.findByNome(cargo.getNome());

            if(optionalNovel.isPresent())
            {
                Novel novel = optionalNovel.get();
                novel.setIdCargo(cargo.getId());

                this.novelRepository.save(novel);
            }
        }

        return this.novelRepository.findAll();

    }

    @Override
    public void atualizarViews(String slug)
    {

        Optional<Novel> optionalNovel = novelRepository.findBySlug(slug);

        if(!optionalNovel.isPresent())
        {
            System.out.println("Novel não encontrada");
            return;
        }

        Novel novel = optionalNovel.get();

    }

    private void reorganizarPorViewsMensais(List<Novel> novels)
    {
        novels.sort((n1, n2) -> Integer.compare(n2.getViewsMensais(), n1.getViewsMensais()));

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
