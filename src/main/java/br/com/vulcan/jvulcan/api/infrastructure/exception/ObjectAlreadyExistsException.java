package br.com.vulcan.jvulcan.api.infrastructure.exception;

import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ObjectAlreadyExistsException extends EntityExistsException
{

    public ObjectAlreadyExistsException(String message)
    {
        super(message);
    }

}
