package br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MensagemJson {

    private String content;
    private List<Embeds> embeds;

}
