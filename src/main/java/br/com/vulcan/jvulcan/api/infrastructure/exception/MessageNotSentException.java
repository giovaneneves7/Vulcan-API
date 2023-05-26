package br.com.vulcan.jvulcan.api.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class MessageNotSentException extends RestClientException
{

    public MessageNotSentException(String message)
    {
        super(message);
    }
}
