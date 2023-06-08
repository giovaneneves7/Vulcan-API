package br.com.vulcan.jvulcan.api.entity.staff.model;

import br.com.vulcan.jvulcan.api.entity.chibata.model.OlhoDaChibata;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import lombok.Data;

import java.util.List;
@Entity
@Table(name = "membros_staff")
@Data
public class Staff
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cargo")
    private String cargo;

    @OneToMany(mappedBy = "autorOuTradutor")
    private List<OlhoDaChibata> olhoDaChibata;

    @JsonProperty("discord_id")
    @Column(name = "discord_id")
    private String discordId;
}
