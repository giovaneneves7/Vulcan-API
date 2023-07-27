package br.com.vulcan.jvulcan.api.entity.novel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "api_todas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Novel {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "colocacao_total")
    private int colocacao;

    @Column(name = "lugar_mes")
    private int colocacaoMensal;

    @Column(name = "total")
    private int viewsTotais;

    @Column(name = "no_mes")
    private int viewsMensais;

    @Column(name = "slug")
    private String slug;

    @Column(name = "nome")
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
    private String nacionalidade;

    @Column(name = "capa")
    private String capa;

    /**
     * Padrão de cadastro: sigla-nome-da-model
     */
    @Column(name = "indice")
    private String indice;

    @Column(name = "autor")
    private String autor;

    @Column(name = "quantidade")
    private String quantidadeCapitulos;

    @Column(name = "funcao")
    private String cargo;

    /**
     * Padrão de cadastro: ativo, completo, pausado, dropado.
     */
    @Column(name = "status")
    private String status;

    @Column(name = "generos", columnDefinition = "TEXT")
    private String generos;

    @Column(name = "escritor")
    private String escritor;

    @Column(name = "estrelinha")
    private String estrelas;

    @Column(name = "tempo")
    private String dataCriacaoIndice;

    @Column(name = "sinopse")
    private String sinopse;

    @Column(name = "id_cargo")
    private String idCargo;

}

