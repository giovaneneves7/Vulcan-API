package br.com.vulcan.jvulcan.api.entity.view.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "views")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class View {

    @Id
    @GeneratedValue
    private UUID id;
    private String categoria;
    @Column(name = "views_totais")
    private long viewsTotais;
    @Column(name = "views_mensais")
    private long viewsMensais;

    public View(String categoria, long viewsTotais, long viewsMensais) {
        this.categoria = categoria;
        this.viewsTotais = viewsTotais;
        this.viewsMensais = viewsMensais;
    }
}
