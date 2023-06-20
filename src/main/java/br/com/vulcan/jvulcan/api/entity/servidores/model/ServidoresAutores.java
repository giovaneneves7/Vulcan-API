package br.com.vulcan.jvulcan.api.entity.servidores.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servidores_autores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServidoresAutores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String webhook;

    @Column
    private String mensagem;

    @Column(name = "id_cargo")
    private String idCargo;

}
