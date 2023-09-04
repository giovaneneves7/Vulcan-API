package br.com.vulcan.jvulcan.api.entity.novel.service;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarCargoNovelDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.request.CadastrarNovelDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelComCargoDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelComRankDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelComRankingTotalAtualizadoDto;
import br.com.vulcan.jvulcan.api.entity.novel.model.dto.response.NovelResponseDto;
import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;

import br.com.vulcan.jvulcan.api.entity.view.model.View;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectAlreadyExistsException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private final String OBJECT_NOT_FOUND = "A novel requisitada não existe na base de dados";

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
    public <DTO extends NovelResponseDto> DTO buscarPorSlug(String slug, Optional<String> filtro) {

        Optional<Novel> optionalNovel = this.novelRepository.findBySlug(slug);

        if(!optionalNovel.isPresent()){
            throw new ObjectAlreadyExistsException(OBJECT_NOT_FOUND);
        }

        if(filtro.isEmpty()){

            return (DTO) new NovelResponseDto(optionalNovel.get().getNome(), optionalNovel.get().getSlug());

        }


        if(filtro.get().equals("rank-total")){

            List<Novel> novels = novelRepository.findAll();

            if(!novels.isEmpty()){

                reorganizarPorViewsMensais(novels);
                reorganizarPorViewsTotais(novels);

                log.info("Colocação reorganizadas");
                novelRepository.saveAll(novels);
                log.info("Novels salvas");

                Optional<Novel> novel = novelRepository.findBySlug(slug);

                return (DTO) new NovelComRankDto(novel.get());

            }


            optionalNovel.get().setColocacao(1);
            optionalNovel.get().setColocacaoMensal(1);

            return (DTO) new NovelComRankDto(optionalNovel.get());

        }

        log.info("Filtro vazio!");
        return (DTO) new NovelResponseDto(optionalNovel.get().getNome(), optionalNovel.get().getSlug());

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

    /**
     * Cadastra ou atualiza o cargo de uma novel.
     * @param novelDto O DTO com os dados do cargo e da novel que será atualizada.
     * @return um DTO com os dados da novel que teve o cargo cadastrado.
     */
    @Override
    public NovelComCargoDto cadastrarCargo(CadastrarCargoNovelDto novelDto) {

        Optional<Novel> optionalNovel = novelRepository.findByNome(novelDto.cargo());

        if(!optionalNovel.isPresent())
            throw new ObjectNotFoundException(OBJECT_NOT_FOUND);

        Novel novel = optionalNovel.get();
        novel.setIdCargo(novelDto.id());

        novelRepository.save(novel);

        log.info("O cargo da novel {} foi atualizado para '{}'", novel.getNome(), novel.getIdCargo());

        return new NovelComCargoDto(novel.getNome(), novel.getIdCargo());
    }

    @Override
    @Transactional
    public void atualizarViews()
    {

        List<Novel> novels = novelRepository.findAll();

        if(novels.isEmpty())
            return;

        OkHttpClient client = new OkHttpClient();

        int tamanhoDoLote = 20;
        int totalNovels = novels.size();

        for(int comecoLote = 0; comecoLote < totalNovels; comecoLote += tamanhoDoLote){
            int fimLote = Math.min(comecoLote + tamanhoDoLote, totalNovels);

            for(int i = comecoLote; i < fimLote; i++){

                Request request = new Request.Builder()
                        .url("https://vulcannovel.com.br/wp-json/nekoyasha7/jvulcan-api/v1/novels/novel/views?novel=".concat(novels.get(i).getSlug()))
                        .build();

                try{
                    Response response = client.newCall(request).execute();

                    if(response.isSuccessful()){
                        String responseBody = response.body().string();

                        log.info("Atualizando views para: {}", novels.get(i).getNome());

                        ObjectMapper objectMapper = new ObjectMapper();
                        View views;
                        try{
                            views = objectMapper.readValue(responseBody, View.class);
                            if(views.getSuccess()){
                                novels.get(i).setViewsTotais(views.getData().getTotalViews());
                            }

                        }catch(UnrecognizedPropertyException ex){
                            log.warn("Erro: {}", ex.getMessage());
                        }

                    } else{
                        log.warn("Erro ao tentar fazer a requisição. Código: {}\nErro: {}", response.code(), response.message());
                    }


                } catch (Exception ex){

                    ex.printStackTrace();

                }

            }

            novelRepository.saveAll(novels.subList(comecoLote, fimLote));
            log.info("Lote atualizado!");
        }


        reorganizarPorViewsTotais(novels);
        novelRepository.saveAll(novels);
        log.info("As colocações das novels foram atualizadas com sucesso!");



    }

    /**
     * Atualiza o ranking total das novels.
     *
     * @return lista com informações das novels que tiveram o ranking atualizado.
     */
    @Override
    @Transactional
    public List<NovelComRankingTotalAtualizadoDto> atualizarRankingTotal() {

        List<Novel> novels = this.novelRepository.findAll();

        List<NovelComRankingTotalAtualizadoDto> novelsDto = reorganizarPorViewsTotais(novels);
        this.novelRepository.saveAll(novels);
        log.info("Rankings das novels atualizados com sucesso!");

        return novelsDto;
    }

    /**
     * Reorganiza uma lista de novels pelo total de views mensais.
     * @param novels A lista com as novels que serão reorganizadas.
     *
     * @return lista com informações de novels que tiveram os seus rankings atualizados com sucesso.
     */
    private List<NovelComRankingTotalAtualizadoDto> reorganizarPorViewsMensais(List<Novel> novels)
    {
        List<NovelComRankingTotalAtualizadoDto> novelsDto = new ArrayList<>();

        novels.sort((n1, n2) -> Integer.compare(n2.getViewsMensais(), n1.getViewsMensais()));

        for(int i = 0; i < novels.size(); i++)
        {
            novels.get(i).setColocacaoMensal(i + 1);
            novelsDto.add(new NovelComRankingTotalAtualizadoDto(novels.get(i).getNome(), novels.get(i).getColocacao()));
        }

        return novelsDto;
    }

    /**
     * Reorganiza uma lista de novels pela quantidade de views totais.
     * @param novels A lista de novels que será reorganizada.
     *
     * @return lista com informações de novels que tiveram os seus rankings atualizados com sucesso.
     */
    private List<NovelComRankingTotalAtualizadoDto> reorganizarPorViewsTotais(List<Novel> novels)
    {

        List<NovelComRankingTotalAtualizadoDto> novelsDto = new ArrayList<>();
        novels.sort((n1, n2) -> Long.compare(n2.getViewsTotais(), n1.getViewsTotais()));

        for(int i = 0; i < novels.size(); i++)
        {
            novels.get(i).setColocacao((i + 1));
            novelsDto.add(new NovelComRankingTotalAtualizadoDto(novels.get(i).getNome(), novels.get(i).getColocacao()));
        }

        return novelsDto;
    }
}
