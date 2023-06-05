package br.com.vulcan.jvulcan.api.entity.chibata.service;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;

import java.util.List;

public interface IOlhoDaChibataService
{

    /**
     * Cadastra dados de staffs e novels na base de dados.
     * @param dadosChibata Os dados que serão cadastrados.
     * @return 'true' caso seja cadastrado com sucesso, 'false' caso não.
     */
    boolean cadastrar(OlhoDaChibata dadosChibata);

    /**
     * Lista todos os dados do Olho da Chibata.
     * @return Lista com dados do Olho da Chibata.
     */
    List<OlhoDaChibata> listarTodos();

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
