package com.laudwilliam.keyvaluedatabase.errors;

public class KeyTypeException extends Exception {
    public KeyTypeException() {
        super();
    }

    public KeyTypeException(String message) {
        super(message);
    }

    public KeyTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyTypeException(Throwable cause) {
        super(cause);
    }

    protected KeyTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public KeyTypeException(String message, String key) {

    }
}
