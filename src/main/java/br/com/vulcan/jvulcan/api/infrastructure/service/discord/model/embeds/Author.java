package br.com.vulcan.jvulcan.api.infrastructure.service.discord.model.embeds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Author {

    private String name;
    private String icon_url;

}
