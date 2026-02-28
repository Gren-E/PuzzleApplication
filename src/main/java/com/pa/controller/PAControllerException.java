package com.pa.controller;

public class PAControllerException extends Exception {

    public PAControllerException() {
        super();
    }

    public PAControllerException(String message) {
        super(message);
    }

    public PAControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PAControllerException(Throwable cause) {
        super(cause);
    }
}
