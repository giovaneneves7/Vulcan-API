package br.com.vulcan.jvulcan.api.entity.novel.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NovelComRankingTotalAtualizadoDto(
        @JsonProperty(value = "nome")
        String nome,

        @JsonProperty(value = "colocacao_total")
        long colocacaoTotal
) {
}
