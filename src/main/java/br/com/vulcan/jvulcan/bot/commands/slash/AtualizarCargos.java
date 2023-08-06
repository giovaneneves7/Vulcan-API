package br.com.vulcan.jvulcan.bot.commands.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class AttTest extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        System.out.println("Here!");
        if(event.getName().equals("att-test")){

            event.getChannel().sendMessage("Pooooong!").queue();
        }
    }
}
