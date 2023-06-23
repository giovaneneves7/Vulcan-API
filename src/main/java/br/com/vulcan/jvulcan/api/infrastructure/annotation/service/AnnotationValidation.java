package br.com.vulcan.jvulcan.api.infrastructure.annotation.service;

import br.com.vulcan.jvulcan.api.infrastructure.annotation.ValidApiKey;

public class AnnotationValidation implements IAnnotationValidation
{

    @Override
    public boolean validarChaveApi(String chaveApi) {

        ValidApiKey anotacao = chaveApi.getClass().getAnnotation(ValidApiKey.class);

        if(anotacao == null)
            throw new IllegalArgumentException("Anotação inválida");

        return (anotacao.apiKey().equals(chaveApi));

    }
}
