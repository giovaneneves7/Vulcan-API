package br.com.vulcan.jvulcan.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse
{
    private final int status;
    private final String message;
    private final String stackTrace;
    private List<ValidationError> erros;

    @Data
    @RequiredArgsConstructor
    public static class ValidationError
    {

        private final String field;
        private final String message;

    }

    /**
     * Adiciona uma nova validação de erro.
     * @param field O campo do erro.
     * @param message A mensagem de erro.
     */
    public void addValidationError(String field, String message)
    {

        if(Objects.isNull(erros))
        {
            this.erros = new ArrayList<>();
        }

        this.erros.add(new ValidationError(field, message));
    }


    public String toJson()
    {
        return "{\"Status\": "
                .concat(String.valueOf(this.getStatus()))
                .concat(", ")
                .concat("\"message\": \"")
                .concat(this.getMessage())
                .concat("\"}");
    }

}
