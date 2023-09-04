package br.com.vulcan.jvulcan.api.entity.novel.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NovelResponseDto {


    @JsonProperty(value = "novel")
    public String nome;

    @JsonProperty(value = "slug")
    public String slug;

    @JsonProperty(value = "rank_total")
    public long rankTotal;

    @JsonProperty(value = "views_totais")
    public long viewsTotais;

    public NovelResponseDto(String nome, String slug){
        this.nome = nome;
        this.slug = slug;
    }
}
