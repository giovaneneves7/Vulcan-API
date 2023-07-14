package br.com.vulcan.jvulcan.api.entity.view.service;

import br.com.vulcan.jvulcan.api.entity.view.model.dto.request.IncrementarViewsDTO;
import br.com.vulcan.jvulcan.api.entity.view.model.dto.response.IncrementarViewsDto;

public interface INovelViewsService {

    /**
     * Incrementa as views totais de uma novel.
     *
     * @param viewsDTO O DTO com dados da novel que terá as views incrementadas.
     *
     * @return Um DTO com os dados atualizados da novel.
     */
     IncrementarViewsDto incrementarViews(IncrementarViewsDTO viewsDTO);
}
