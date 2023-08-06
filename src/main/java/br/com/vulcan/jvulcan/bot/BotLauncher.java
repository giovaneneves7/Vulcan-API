package br.com.vulcan.jvulcan.bot;

import br.com.vulcan.jvulcan.bot.commands.slash.AtualizarCargos;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Component
public class BotLauncher extends ListenerAdapter
{

    private String TOKEN;

    private JDA jda;

    public BotLauncher(@Value("${token}") String token) {
        TOKEN = token;
    }

    public void iniciarBot() throws LoginException
    {
        jda = JDABuilder.createDefault(TOKEN)
                .addEventListeners(this)
                .build();

        this.registerSlashCommands();
    }

    public JDA getJda(){

        return this.jda;
    }

    @Override
    public void onReady(@NotNull final ReadyEvent event)
    {
        System.out.println("O bot está pronto");

        super.onReady(event);
    }

    public void registerSlashCommands(){

        jda.upsertCommand("atualizar-cargos", "a simple test")
                .queue();

        jda.addEventListener(new AtualizarCargos());
    }
}
