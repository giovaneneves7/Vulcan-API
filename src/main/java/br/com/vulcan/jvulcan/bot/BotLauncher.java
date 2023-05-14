package br.com.vulcan.jvulcan.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Component
public class BotLauncher extends ListenerAdapter
{

    private final String TOKEN = "MEU_TOKEN";

    public BotLauncher() throws LoginException
    {
        JDABuilder.createDefault(TOKEN)
                .addEventListeners(this)
                .build();
    }

    @Override
    public void onReady(@NotNull final ReadyEvent event)
    {
        System.out.println("O bot está pronto");
        super.onReady(event);
    }

//--+ Eventos aqui +--//
}
