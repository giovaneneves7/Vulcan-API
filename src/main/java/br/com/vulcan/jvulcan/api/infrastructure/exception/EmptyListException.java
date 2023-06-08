package br.com.vulcan.jvulcan.api.infrastructure.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class EmptyListException extends EntityNotFoundException {

    public EmptyListException()
    {
        super();
    }

    public EmptyListException(String message)
    {
        super(message);
    }
}
