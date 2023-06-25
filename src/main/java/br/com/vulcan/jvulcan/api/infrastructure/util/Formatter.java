package br.com.vulcan.jvulcan.api.infrastructure.util;

public class Formatter {

    private final String pattern = "-\\\\d+x\\\\d+";

    /**
     * Formata URL de capas de novel, removendo as dimensões para as imgens serem carregadas corretamente.
     *
     * @param capaUrl A url da capa que será formatada.
     * @return url formatada.
     */
    public String formatarUrlDeCapa(String capaUrl)
    {

        return capaUrl.replaceAll(pattern, "");

    }
}
