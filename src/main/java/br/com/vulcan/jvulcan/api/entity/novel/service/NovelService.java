package br.com.vulcan.jvulcan.api.entity.novel.service;

import br.com.vulcan.jvulcan.api.entity.cargo.model.Cargo;
import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarNovelDto;
import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;

import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectAlreadyExistsException;
import br.com.vulcan.jvulcan.bot.BotLauncher;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
     * Salva uma novel na base de dados.
     * @param novelDto O DTO com dados da novel que será salva.
     * @return 'true' caso a novel seja salva, 'false' caso contrário.
     */
    @Override
    @Transactional
    public Novel salvar(final CadastrarNovelDto novelDto)
    {

        log.info("Tentando salvar novel {}", novelDto.titulo());
        Novel novel = novelDto.converter();

        List<Novel> novels = this.novelRepository.findAll();

        //--+ Verifica se há registro no banco de dados +--//
        if(novels.isEmpty())
        {
            novel.setColocacao(1);
            novel.setColocacaoMensal(1);
            novelRepository.save(novel);

            log.info("A novel {} foi salva na base de dados", novel.getNome());
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
            novel.setColocacao(novels.size() + 1);
            novel.setColocacaoMensal(novels.size() + 1);
            novel.setId(novels.size() + 1);
            log.info("A novel {} será salva com a colocação total {} e colocação mensal {}", novel.getNome(), novel.getColocacao(), novel.getColocacaoMensal());
            this.novelRepository.save(novel);

            return novel;
        } else{
            log.error("A novel {} já existe na base de dados", novel.getNome());
            throw new ObjectAlreadyExistsException(OBJECT_ALREADY_EXISTS);
        }

    }

    /**
     * Deleta a novel com o ID passado por parâmetro.
     * @param id O ID da novel a ser deletada.
     */
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

        return optionalNovel.orElse(null);

    }

    /**
     * Atualiza o cargo das novels.
     * @param jda O objeto que permite a comunicação à API do Discord.
     * @return Lista com as novels na base de dados.
     */
    @Override
    @Transactional
    public List<Novel> atualizarCargo(JDA jda)
    {

        List<Novel> novelsSemCargo = novelRepository.findNovelWithNullIdCargo();
        List<Novel> novelsAtualizadas = new ArrayList<>();

        for(Novel novel : novelsSemCargo){

            List<Role> roles = jda.getRolesByName(novel.getNome(), true);

            if(!roles.isEmpty() && roles.get(0).getName().equals(novel.getNome())){

                log.info("Atualizando cargo da novel {}", novel.getNome());
                novel.setIdCargo(roles.get(0).getId());
                novelRepository.save(novel);
                novelsAtualizadas.add(novel);
            }

        }

        return novelsSemCargo;

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
