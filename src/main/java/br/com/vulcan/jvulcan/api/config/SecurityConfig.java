package br.com.vulcan.jvulcan.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig
{

    private final static List<String> PUBLIC_MATCHERS = List.of("/");
    private final static List<String> PUBLIC_MATCHERS_POST = List.of(
                                                                        "/users/user",
                                                                        "/login"
                                                                    );

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {

        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //--+ Rotas autorizadas +--//


        return http.build();

    }
}
