package br.com.vulcan.jvulcan.api.entity.chibata.service;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;
import br.com.vulcan.jvulcan.api.entity.chibata.repository.OlhoDaChibataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OlhoDaChibataService implements IOlhoDaChibataService
{

    @Autowired
    OlhoDaChibataRepository chibataRepository;

    /**
     * Lista todos os dados do Olho da Chibata.
     * @return Lista com dados do Olho da Chibata.
     */
    @Override
    public List<OlhoDaChibata> listarTodos()
    {

        return this.chibataRepository.findAll();

    }
}
