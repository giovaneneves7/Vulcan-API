package br.com.vulcan.jvulcan.api.entity.chibata.service;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;
import br.com.vulcan.jvulcan.api.entity.chibata.model.dto.request.CadastrarDadosChibataDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IOlhoDaChibataService
{

    /**
     * Cadastra dados de staffs e novels na base de dados.
     *
     * @param dadosChibataDto Os dados que serão cadastrados.
     */
    void cadastrarDadosChibata(CadastrarDadosChibataDto dadosChibataDto);

    /**
     * Lista todos os dados do Olho da Chibata.
     * @return Lista com dados do Olho da Chibata.
     */
    List<OlhoDaChibata> listarTodos(Optional<Pageable> pageableOptional);

    /**
     * Atualiza os dados de um registro existente.
     *
     * @param dadosChibata Os dados a serem atualizados
     * @return 'true' caso os dados sejam atualizados com sucesse, 'false' caso contrário.
     */
    boolean atualizar(OlhoDaChibata dadosChibata);

    /**
     * Busca um cadastro por id.
     *
     * @param id O ID do registro a ser buscado.
     * @return O objeto caso ele exista, 'null' caso não.
     */
    OlhoDaChibata encontrarPorId(long id);

    /**
     * Cobra membros com 7 dias sem postagem.
     * @return 'true' caso a cobrança seja realizada com sucesso, 'false' caso não.
     */
    boolean cobrarBaianos();
}
