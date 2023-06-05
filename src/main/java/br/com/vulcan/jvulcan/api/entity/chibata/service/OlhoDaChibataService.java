package br.com.vulcan.jvulcan.api.entity.chibata.service;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;
import br.com.vulcan.jvulcan.api.entity.chibata.repository.OlhoDaChibataRepository;

import br.com.vulcan.jvulcan.api.entity.novel.repository.NovelRepository;
import br.com.vulcan.jvulcan.api.infrastructure.exception.MessageNotSentException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OlhoDaChibataService implements IOlhoDaChibataService
{

    private final String MESSAGE_NOT_SENT = "Mensagem não enviada devido a conflitos com o provedor do serviço";
    private final long SEMANA = 7;
    private final long MES = 30;
    private final long ANO = 1;

    @Value("${webhoook_url}")
    private String webhookUrl;
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
     * @param dadosChibata Os dados que serão cadastrados.
     * @return 'true' caso seja cadastrado com sucesso, 'false' caso não.
     */
    @Override
    public boolean cadastrar(OlhoDaChibata dadosChibata) {

        if(!novelRepository.findAll().stream().anyMatch(novel -> novel.getNome().equals(dadosChibata.getNovel())))
        {
            return false;
        }

        this.chibataRepository.save(dadosChibata);

        return true;

    }

    /**
     * Lista todos os dados do Olho da Chibata.
     * @return Lista com dados do Olho da Chibata.
     */
    @Override
    public List<OlhoDaChibata> listarTodos()
    {

        return this.chibataRepository.findAll();

    }

    /**
     * Cobra membros com 7 dias sem postagem.
     * @return 'true' caso a cobrança seja realizada com sucesso, 'false' caso não.
     */
    @Override
    public boolean cobrarBaianos()
    {
        HashMap<Long, String> baianos = new HashMap<>();

        List<OlhoDaChibata> dados = chibataRepository.findAll();

        if(dados.isEmpty())
        {
            return false;
        }

        for(OlhoDaChibata dado : dados)
        {

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
            LocalDateTime dataEHoraAgora  = LocalDateTime.now();
            LocalDateTime dataEHoraUltimaPostagem = LocalDateTime.parse(dado.getUltimaPostagem(), formato);

            long diasSemPostar = ChronoUnit.DAYS.between(dataEHoraUltimaPostagem, dataEHoraAgora);

            if(diasSemPostar > 7)
            {
                baianos.put(diasSemPostar, dado.getAutorOuTradutor().getDiscordId());
            }

        }

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(this.webhookUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(criarMensagemCobranca(baianos).toString(), StandardCharsets.UTF_8))
                .build();

        try{

            HttpResponse<String> response = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204) {
                log.info("Mensagem enviada com sucesso!");
            } else {

                log.error("Mensagem não enviada");
                throw new MessageNotSentException(MESSAGE_NOT_SENT);
            }
        } catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    private StringBuilder criarMensagemCobranca(HashMap<Long, String> baianos)
    {

        String mensagemAviso = " ";
        StringBuilder mensagem = null;

        for(Map.Entry<Long, String> baiano : baianos.entrySet())
        {

            if(baiano.getKey() > 60)
            {
                mensagemAviso = "Sua novel será colocada como dropada caso não haja novas postagens.";
            }
            mensagem.append
                            (
                                "<@%".concat(baiano.getValue())
                                     .concat(">, ")
                                     .concat(contarDias(baiano.getKey()))
                                     .concat(" sem postar")
                                     .concat(mensagemAviso)
                                     .concat("\n")
                            );

        }

        return mensagem;
    }

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

    private long contador(long diasSemPostar, long salto)
    {

        int cont = 0;

        for(;diasSemPostar != 0; diasSemPostar -= salto )
        {
            cont++;
        }

        return cont;
    }




}
