package br.com.vulcan.jvulcan.api.entity.novel.model.dto.response;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NovelComRankDto extends NovelResponseDto{

    @JsonProperty(value = "colocacao_total")
    int colocacaoTotal;

    @JsonProperty(value = "colocacao_mensal")
    int colocacaoMensal;

    public NovelComRankDto(Novel novel){

        this.slug = novel.getSlug();
        this.nome = novel.getNome();
        this.colocacaoTotal = novel.getColocacao();
        this.colocacaoMensal = novel.getColocacaoMensal();

    }

}
