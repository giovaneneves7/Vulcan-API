package br.com.vulcan.jvulcan.api.entity.servidores.model;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servidores_autores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServidorAutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String webhook;

    @Column
    private String mensagem;

    @Column(name = "id_cargo")
    private String idCargo;

    @OneToOne
    @JoinColumn(name = "novel_id")
    private Novel novel;
}
