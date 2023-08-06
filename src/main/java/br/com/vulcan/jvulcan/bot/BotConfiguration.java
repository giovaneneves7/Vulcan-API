package br.com.vulcan.jvulcan.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class BotConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void initializeBot() throws LoginException {
        BotLauncher botLauncher = applicationContext.getBean(BotLauncher.class);
        botLauncher.iniciarBot();
    }

}


