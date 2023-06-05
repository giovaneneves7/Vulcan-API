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
     * Cobra membros com 7 dias sem postagem.
     * @return 'true' caso a cobrança seja realizada com sucesso, 'false' caso não.
     */
    boolean cobrarBaianos();
}
