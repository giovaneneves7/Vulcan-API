package br.com.vulcan.jvulcan.api.entity.servidores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 5, message = "A URL do webhoook precisa ter entre 5 e 80 caracteres")
    private String webhook;

    @Column
    @Size(min = 5, message = "A descrição precisa ter entre 5 e 50 caracteres")
    private String mensagem;

    @Column(name = "id_cargo")
    private String idCargo;

}
