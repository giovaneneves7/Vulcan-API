package br.com.vulcan.jvulcan.api.config;

import br.com.vulcan.jvulcan.api.infrastructure.origin.OrigensAutorizadas;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private String[] origens;

    public CorsConfig()
    {
        origens = Arrays.stream(OrigensAutorizadas.values())
                .map(OrigensAutorizadas::getUrl)
                .toArray(String[]::new);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins(origens);
    }
}
