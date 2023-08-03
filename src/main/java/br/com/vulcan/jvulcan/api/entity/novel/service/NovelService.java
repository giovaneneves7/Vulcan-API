package br.com.vulcan.jvulcan.api.entity.novel.service;

import br.com.vulcan.jvulcan.api.entity.cargo.model.Cargo;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarNovelDto;
import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;

import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectAlreadyExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NovelService implements INovelService
{

    private final String OBJECT_ALREADY_EXISTS = "A novel já existe na base de dados";

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
     * Salva uma model na base de dados.
     * @param novelDto O DTO com dados da novel que será salva.
     * @return 'true' caso a model seja salva, 'false' caso contrário.
     */
    @Override
    @Transactional
    public Novel salvar(final CadastrarNovelDto novelDto)
    {

        Novel novel = novelDto.converter();

        List<Novel> novels = this.novelRepository.findAll();

        //--+ Verifica se há registro no banco de dados +--//
        if(novels.isEmpty())
        {
            novel.setColocacao(1);
            novel.setColocacaoMensal(1);
            novelRepository.save(novel);

            return novel;
        }

        // --+ Verifica se já há alguma novel com o slug na base de dados +--//
        boolean slugExiste = novels.stream()
                                      .anyMatch(n -> n.getSlug().equals(novel.getSlug()));

        //--+ Verifica se já há alguma novel com o indice na base de dados +--//
        boolean indiceExiste = novels.stream()
                .anyMatch(n -> n.getSlug().equals(novel.getIndice()));


        //--+ Organiza a novel de acordo com as views +--//
        if(!slugExiste && !indiceExiste)
        {
            this.novelRepository.save(novel);

            return novel;
        } else{

            throw new ObjectAlreadyExistsException(OBJECT_ALREADY_EXISTS);
        }

    }

    /**
     * Deleta a model com o ID passado por parâmetro.
     * @param id O ID da model a ser deletada.
     */
    @Override
    public void deletar(long id)
    {

        this.novelRepository.deleteById(id);

    }

    /**
     * Busca uma model pelo slug passado.
     * @param slug O slug da model.
     * @return A model com o slug passado por parâmetro, 'null' caso ela não exista.
     */
    @Override
    public Novel buscarPorSlug(String slug) {

        Optional<Novel> optionalNovel = this.novelRepository.findBySlug(slug);

        return optionalNovel.orElse(null);

    }

    /**
     * Atualiza o cargo das novels.
     * @param cargos A lista com cargos do servidor do discord da Vulcan.
     * @return Lista com as novels na base de dados.
     */
    @Override
    @Transactional
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

    /**
     * Reorganiza uma lista de novels pelo total de views mensais.
     * @param novels A lista com as novels que serão reorganizadas.
     */
    private void reorganizarPorViewsMensais(List<Novel> novels)
    {
        novels.sort((n1, n2) -> Integer.compare(n2.getViewsMensais(), n1.getViewsMensais()));

        for(int i = 0; i < novels.size(); i++)
        {
            novels.get(i).setColocacaoMensal(i + 1);
        }
    }

    /**
     * Reorganiza uma lista de novels pela quantidade de views totais.
     * @param novels A lista de novels que será reorganizada.
     */
    private void reorganizarPorViewsTotais(List<Novel> novels)
    {
        novels.sort((n1, n2) -> Integer.compare(n2.getViewsTotais(), n1.getViewsTotais()));

        for(int i = 0; i < novels.size(); i++)
        {
            novels.get(i).setColocacao((i + 1));
        }
    }
}
