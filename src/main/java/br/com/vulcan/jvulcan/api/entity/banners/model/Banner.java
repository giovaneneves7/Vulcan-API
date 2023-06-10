package br.com.vulcan.jvulcan.api.entity.banners.model;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "api_banners")
@Data
public class Banner
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    @JsonProperty(value = "novel")
    @ManyToOne
    @JoinColumn(name = "novel_id", referencedColumnName = "id")
    private Novel novel;

    @Column(unique = true)
    private String link;
}
