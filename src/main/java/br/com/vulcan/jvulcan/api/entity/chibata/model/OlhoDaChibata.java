package br.com.vulcan.jvulcan.api.entity.chibata.model;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import br.com.vulcan.jvulcan.api.entity.staff.model.Staff;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "olho_da_chibata")
@Data
public class OlhoDaChibata
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "novel_id")
    private Novel novel;

    @Column(name = "ultima_postagem")
    private String ultimaPostagem;

    @ManyToOne
    @JoinColumn(name  = "staff_id")
    private Staff autorOuTradutor;

}
