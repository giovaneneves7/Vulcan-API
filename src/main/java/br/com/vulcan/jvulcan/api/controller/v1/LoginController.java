package br.com.vulcan.jvulcan.api.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController
{

    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

    public LoginController(OAuth2AuthorizedClientService auth2AuthorizedClientService)
    {
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
    }

    @GetMapping(path = "/login/oauth2/code/discord")
    public String handleDiscordAuthorizationCode
    (
        @RequestParam("code") String authorizationCode,
        @RequestParam("state") String state,
        @RequestParam("code_verifier") String code_verifier,
        OAuth2AuthenticationToken authentication
    )
    {
        log.info("ENTROU AQUI");
        System.out.println("code verifier" + code_verifier);

        OAuth2AuthorizedClient authorizedClient = getAuthorizedClient(authentication);

        return "<h1> Hello, ".concat(authorizedClient.getPrincipalName()).concat("<h1>");
    }

    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication)
    {

        return auth2AuthorizedClientService.loadAuthorizedClient
                (
                    authentication.getAuthorizedClientRegistrationId(),
                    authentication.getName()
                );
    }


}
