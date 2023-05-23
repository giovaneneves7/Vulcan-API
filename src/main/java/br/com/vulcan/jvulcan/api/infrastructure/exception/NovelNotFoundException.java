package br.com.vulcan.jvulcan.api.infrastructure.exception;

public class NovelNotFoundException extends Exception
{
    public NovelNotFoundException() {
        super();
    }

    public NovelNotFoundException(String message) {
        super(message);
    }

    public NovelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NovelNotFoundException(Throwable cause) {
        super(cause);
    }

    protected NovelNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
