package br.com.vulcan.jvulcan.api.infrastructure.origin;

public enum OrigensAutorizadas
{

    VULCAN("https://vulcannovel.com.br/"),
    APILL("https://apill.vulcannovel.com.br/"),
    LOCAL_HOST("http://localhost:3000/");

    private final String url;

    private OrigensAutorizadas(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return this.url;
    }
}
