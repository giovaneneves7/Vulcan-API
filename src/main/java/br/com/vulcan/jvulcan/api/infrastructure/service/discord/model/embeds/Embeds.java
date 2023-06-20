package br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Embeds {

    private String title;
    private String url;
    private Author author;
    private String color;
    private Footer footer;
}
