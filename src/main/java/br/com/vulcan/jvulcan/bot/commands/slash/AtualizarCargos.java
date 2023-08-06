package br.com.vulcan.jvulcan.bot.commands.slash;


import br.com.vulcan.jvulcan.api.entity.novel.service.INovelService;
import br.com.vulcan.jvulcan.bot.BotLauncher;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AtualizarCargos extends ListenerAdapter {

    @Autowired
    INovelService novelService;

    @Value("${token}")
    String token;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if(event.getName().equals("atualizar-cargos")){

            if(event.getMember().hasPermission(Permission.BAN_MEMBERS)){

                JDA jda = new BotLauncher(token).getJda();

                novelService.atualizarCargo(jda);

            } else{
                event.reply("Bleh! Você é fraco, falta-lhe permissões para usar este comando!")
                        .setEphemeral(true)
                        .queue();
            }
        }
    }
}
