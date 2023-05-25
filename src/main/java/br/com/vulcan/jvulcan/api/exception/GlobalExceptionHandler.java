package br.com.vulcan.jvulcan.api.exception;

import br.com.vulcan.jvulcan.api.infrastructure.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{

    @Value(value = "${server.error.include-exception}")
    private boolean printStackTrace;

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request)
    {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. Check 'errors' field for details.");

        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors())
        {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse);

    }

    /**
     * Pega todas as exceções genéricas.
     * @param exception A exceção genérica.
     * @param request A requisição que ocasionou o disparo da exceção.
     * @return mensagem de erro.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
                                                                Exception exception,
                                                                WebRequest request)
    {

        final String mensagemErro = "Unknown error occurred";

        log.error(mensagemErro, exception);

        return construirMensagemDeErro(
                exception,
                mensagemErro,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    //--+ Tratamento de exceções personalizadas +--//
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleObjectNotFoundException(
                                                                ObjectNotFoundException objectNotFoundException,
                                                                WebRequest request)
    {
        log.error("Failed to find the requested element", objectNotFoundException);

        return construirMensagemDeErro(
                                        objectNotFoundException,
                                        objectNotFoundException.getMessage(),
                                        HttpStatus.NOT_FOUND,
                                        request
                                       );
    }



    /**
     * Constroi uma mensagem de erro.
     * @param exception A exceção.
     * @param message A mensagem de exceção.
     * @param httpStatus O status da requisição.
     * @param request A requisição.
     * @return a mensagem de erro.
     */
    private ResponseEntity<Object> construirMensagemDeErro(
                                                            Exception exception,
                                                            String message,
                                                            HttpStatus httpStatus,
                                                            WebRequest request)
    {

        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if (this.printStackTrace)
        {
            errorResponse.setStacktrace(ExceptionUtils.getStackTrace(exception));
        }

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
