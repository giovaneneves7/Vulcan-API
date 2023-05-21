package br.com.vulcan.jvulcan.api.entity.novel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Data;

@Entity
@Table(name = "api_novels")
@Data
public class Novel {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @Column(name = "lugar", unique = true)
    @JsonProperty("colocacao_total")
    private int colocacao;

    @Column(name = "lugar_mes")
    @JsonProperty("colocacao_mensal")
    private int colocacaoMensal;

    @Column(name = "total")
    @JsonProperty("visualizacoes_totais")
    private int viewsTotais;

    @Column(name = "no_mes")
    @JsonProperty("visualizacoes_mensais")
    private int viewsMensais;

    @Column(name = "slug")
    @JsonProperty("slug")
    private String slug;

    @Column(name = "nome")
    @JsonProperty("nome_projeto")
    private String nome;

    /**
     * Padrão de cadastro:
     * - oci: ocidental
     * - ch: chinesa
     * - jp: japonesa
     * - br: brasileira
     * - co: coreana
     */
    @Column(name = "terra")
    @JsonProperty("nacionalidade")
    private String nacionalidade;

    @Column(name = "capa")
    @JsonProperty("capa")
    private String capa;

    /**
     * Padrão de cadastro: sigla-nome-da-novel
     */
    @Column(name = "indice")
    @JsonProperty("indice")
    private String indice;

    @Column(name = "autor")
    @JsonProperty("autor_ou_tradutor")
    private String autor;

    @Column(name = "quantidade")
    @JsonIgnore
    private String quantidadeCapitulos;

    @Column(name = "funcao")
    @JsonProperty("cargo")
    private String cargo;

    /**
     * Padrão de cadastro: ativo, completo, pausado, dropado.
     */
    @Column(name = "status")
    @JsonProperty("status_projeto")
    private String status;

    @Column(name = "generos", columnDefinition = "TEXT")
    @JsonIgnore
    private String generos;

    @Column(name = "escritor")
    @JsonIgnore
    private String escritor;

    @Column(name = "estrelinha")
    @JsonProperty("nota_media_projeto")
    private String estrelas;

    @Column(name = "tempo")
    @JsonProperty("data_criacao_indice")
    private String dataCriacaoIndice;

    @Column(name = "sinopse")
    @JsonProperty("sinopse")
    private String sinopse;

    @Column(name = "id_cargo")
    @JsonProperty("id_cargo")
    private String idCargo;

}

