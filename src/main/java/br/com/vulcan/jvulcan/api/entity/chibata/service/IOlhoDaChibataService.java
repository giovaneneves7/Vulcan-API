package br.com.vulcan.jvulcan.api.entity.chibata.service;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;

import java.util.List;

public interface IOlhoDaChibataService
{

    /**
     * Lista todos os dados do Olho da Chibata.
     * @return Lista com dados do Olho da Chibata.
     */
    List<OlhoDaChibata> listarTodos();
}
