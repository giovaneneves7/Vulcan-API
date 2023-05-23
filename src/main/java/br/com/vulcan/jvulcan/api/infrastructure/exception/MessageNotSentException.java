package br.com.vulcan.jvulcan.api.infrastructure.exception;

public class MessageNotSentException extends Exception
{
    public MessageNotSentException() {
        super();
    }

    public MessageNotSentException(String message) {
        super(message);
    }

    public MessageNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageNotSentException(Throwable cause) {
        super(cause);
    }

    protected MessageNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
