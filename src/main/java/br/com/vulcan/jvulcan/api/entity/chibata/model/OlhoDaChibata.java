package br.com.vulcan.jvulcan.api.entity.chibata.model;

import br.com.vulcan.jvulcan.api.entity.staff.model.Staff;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Data;

@Entity
@Table(name = "olho_da_chibata")
@Data
public class OlhoDaChibata
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("nome_novel")
    private String novel;

    @JsonProperty("ultima_postagem")
    @Column(name = "ultima_postagem")
    private String ultimaPostagem;

    @JsonProperty("autor_ou_tradutor")
    @ManyToOne
    @JoinColumn(name  = "staff_id")
    private Staff autorOuTradutor;

}
