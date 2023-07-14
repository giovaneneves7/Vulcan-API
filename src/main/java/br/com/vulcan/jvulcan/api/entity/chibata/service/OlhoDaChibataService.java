package br.com.vulcan.jvulcan.api.entity.chibata.service;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;
import br.com.vulcan.jvulcan.api.entity.chibata.model.dto.request.CadastrarDadosChibataDto;
import br.com.vulcan.jvulcan.api.entity.chibata.repository.OlhoDaChibataRepository;

import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;
import br.com.vulcan.jvulcan.api.infrastructure.exception.EmptyListException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.MessageNotSentException;
import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;
import br.com.vulcan.jvulcan.api.infrastructure.service.discord.IWebHookMessageDelivererService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class OlhoDaChibataService implements IOlhoDaChibataService
{

    private final String MESSAGE_NOT_SENT = "Mensagem não enviada devido a conflitos com o provedor do serviço";
    private final String NOVEL_NOT_FOUND = "A model não relacionada ao membro da staff não existe na base de dados.";
    private final String EMPTY_LIST = "A lista de novels está vazia.";
    private final long SEMANA = 7;
    private final long MES = 30;
    private final long ANO = 1;

    @Value("${cobranca_webhook_url}")
    private String webhookUrl;

    @Autowired
    IWebHookMessageDelivererService webHookService;
    @Autowired
    NovelRepository novelRepository;

    @Autowired
    OlhoDaChibataRepository chibataRepository;

    @PostConstruct
    public void init()
    {
        log.info("Conectado ao Webhook: ".concat(this.webhookUrl));
    }

    /**
     * Cadastra dados de ‘staffs’ e novels na base de dados.
     *
     * @param dadosChibataDto Os dados que serão cadastrados.
     */
    @Override
    public void cadastrarDadosChibata(CadastrarDadosChibataDto dadosChibataDto) {

        OlhoDaChibata dadosChibata = new OlhoDaChibata();
        dadosChibataDto.converter(dadosChibata, novelRepository.findByNome(dadosChibataDto.novel()));

        if(!novelRepository.findAll().contains(dadosChibata.getNovel()))
            throw new ObjectNotFoundException(NOVEL_NOT_FOUND);

        this.chibataRepository.save(dadosChibata);

    }

    /**
     * Lista todos os dados do Olho da Chibata.
     * @return Lista com dados do Olho da Chibata.
     */
    @Override
    public List<OlhoDaChibata> listarTodos(Optional<Pageable> optionalPageable)
    {

        return optionalPageable.map(pageable -> chibataRepository.findAll(pageable).getContent())
                .orElseGet(() -> chibataRepository.findAll());

    }

    /**
     * Atualiza os dados de um registro existente.
     *
     * @param dadosChibata Os dados a serem atualizados
     * @return 'true' caso os dados sejam atualizados com sucesse, 'false' caso contrário.
     */
    @Override
    public boolean atualizar(OlhoDaChibata dadosChibata)
    {

        if(!chibataRepository.findAll().contains(dadosChibata))
        {
            return false;
        }

        log.info("Registro atualizado com sucesso!");
        this.chibataRepository.save(dadosChibata);
        return true;
    }

    /**
     * Busca um cadastro por id.
     *
     * @param id O ID do registro a ser buscado.
     * @return O objeto caso ele exista, 'null' caso não.
     */
    @Override
    public OlhoDaChibata encontrarPorId(long id)
    {

        Optional<OlhoDaChibata> optionalDadosChibata = this.chibataRepository.findById(id);

        return optionalDadosChibata.orElse(null);

    }

    /**
     * Cobra membros com 7 dias sem postagem.
     * @return 'true' caso a cobrança seja realizada com sucesso, 'false' caso não.
     */
    @Override
    public boolean cobrarBaianos()
    {
        HashMap<String, List<?>> baianos = new HashMap<>();

        List<OlhoDaChibata> dados = chibataRepository.findAll();

        if(dados.isEmpty())
        {
            return false;
        }

        int cont =  0;
        for(OlhoDaChibata dado : dados)
        {

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
            LocalDateTime dataEHoraAgora  = LocalDateTime.now();
            LocalDateTime dataEHoraUltimaPostagem = LocalDateTime.parse(dado.getUltimaPostagem(), formato);

            long diasSemPostar = ChronoUnit.DAYS.between(dataEHoraUltimaPostagem, dataEHoraAgora);

            if(diasSemPostar > 7)
            {

            baianos.put("Membro ".concat(String.valueOf(cont)), new ArrayList<>(List.of(dado.getAutorOuTradutor().getDiscordId(), diasSemPostar, dado.getNovel())));
            cont++;

            }

        }

        //--+ Envia mensagem para o WebHook especificado +--//
       /* if (!webHookService.enviarMensagem(this.webhookUrl, criarMensagemCobranca(baianos).toString()))
        {

            log.error("Mensagem não enviada");
            throw new MessageNotSentException(MESSAGE_NOT_SENT);

        }*/

        log.info("Mensagem enviada com sucesso!");
        return true;

    }

    /**
     * Cria uma mensagem de cobrança de membros que estão há mais de 7 dias sem postar.
     *
     * @param baianosMap O HashMap com informações dos membros com postagens atrasadas.
     * @return A mensagem que será enviada.
     */
    private StringBuilder criarMensagemCobranca(HashMap<String, List<?>> baianosMap)
    {

        String mensagemAviso = " ";
        StringBuilder mensagem = null;

        for(Map.Entry<String, List<?>> baiano : baianosMap.entrySet())
        {

            long diasSemPostar = (long) baiano.getValue().get(1);

            if(diasSemPostar > 60)
            {
                mensagemAviso = "Sua model será colocada como dropada caso não haja novas postagens.";
            }
            mensagem.append
                            (
                                "<@%".concat(String.valueOf(baiano.getValue().get(0)))
                                     .concat(">, ")
                                     .concat(contarDias(diasSemPostar))
                                     .concat(" sem postar capítulos de ")
                                     .concat(String.valueOf(baiano.getValue().get(2)))
                                     .concat("( ".concat(mensagemAviso).concat(")"))
                                     .concat("\n")
                                        .concat("◆━━━━━━◆❃◆━━━━━━◆\n")
                            );

        }

        return mensagem;
    }

    /**
     * Conta os dias sem postar de um usuário.
     *
     * @param diasSemPostar O total de dias sem postar.
     * @return O total de tempo sem postar. Retornos:
     *                                               1 semana;
     *                                               {variável} semanas;
     *                                               1 mês;
     *                                               {variável} meses;
     *                                               1 ano;
     *                                               {variável} anos.
     */
    public String contarDias(long diasSemPostar)
    {

        long semanasSemPostar = (contador(diasSemPostar, SEMANA));
        long mesesSemPostar = (contador(diasSemPostar, MES));
        long anosSemPostar = (contador(diasSemPostar, ANO));

        String tempoSemPostar = (semanasSemPostar == 1) ? "1 semana" :
                         (semanasSemPostar >= 4) ?
                         (mesesSemPostar == 1) ? "1 mês" :
                         (mesesSemPostar < 365) ? String.valueOf(mesesSemPostar).concat(" meses") :
                         (anosSemPostar == 1) ? "1 ano" : String.valueOf(anosSemPostar).concat(" anos")
                         : String.valueOf(semanasSemPostar).concat(" semanas");

        return tempoSemPostar;

    }

    /**
     * Conta os dias sem postar de um usuário e retorna o tempo sem postar.
     * @param diasSemPostar A quantidade de dias sem postar.
     * @param salto O salto do contador. Saltos válidos: {const} SEMANA, {const} MÊS, {const} ANO.
     * @return o tempo sem postar. Retornos:
     *                                      Total de semanas sem postar (if salto == {const} SEMANA);
     *                                      Total de meses sem postar (if salto == {const} MES);
     *                                      Total de anos sem postar (if salto == {const} ANOS).
     */
    private long contador(long diasSemPostar, long salto)
    {

        int cont = 0;

        for(;diasSemPostar >= salto; diasSemPostar -= salto )
        {
            cont++;
        }

        return cont;
    }




}
