package br.com.vulcan.jvulcan.api.infrastructure.enums;

public enum Errors {

    API_PERMISSION_ERROR("api_permission_error", "Você não tem permissão para acessar este endpoint, bleh!");

    private final String KEY;
    private final String ERROR;

    private Errors(final String key, final String error){
        this.ERROR = error;
        this.KEY = key;
    }

    public String getKey(){
        return KEY;
    }

    public String getErro(){
        return ERROR;
    }

}
